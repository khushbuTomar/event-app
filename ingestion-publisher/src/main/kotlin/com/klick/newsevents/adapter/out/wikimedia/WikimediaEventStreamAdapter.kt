package com.klick.newsevents.adapter.out.wikimedia

import com.klick.newsevents.application.port.out.WikiChangeSource
import com.klick.newsevents.domain.WikiChange
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientRequestException
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import reactor.util.retry.Retry
import java.time.Duration

@Component
class WikimediaEventStreamAdapter(
    webClientBuilder: WebClient.Builder
) : WikiChangeSource {
    private val webClient = webClientBuilder
        .baseUrl(WIKIMEDIA_BASE_URL)
        .build()

    override fun streamChanges(): Flux<WikiChange> =
        webClient.get()
            .uri(RECENT_CHANGE_PATH)
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(object : ParameterizedTypeReference<ServerSentEvent<WikimediaRecentChangeDto>>() {})
            .filter { event -> event.data() != null }
            .map { event -> event.data()!!.toDomain() }
            .doOnSubscribe { logger.info("Connected to Wikimedia EventStreams") }
            .doOnError { error -> logger.warn("Wikimedia EventStreams connection failed", error) }
            .doOnCancel { logger.info("Disconnected from Wikimedia EventStreams") }
            .retryWhen(
                Retry.backoff(Long.MAX_VALUE, INITIAL_RETRY_DELAY)
                    .maxBackoff(MAX_RETRY_DELAY)
                    .jitter(RETRY_JITTER)
                    .filter(::isRetryable)
                    .doBeforeRetry { signal ->
                        logger.warn(
                            "Retrying Wikimedia EventStreams connection; attempt {}",
                            signal.totalRetries() + 1,
                            signal.failure()
                        )
                    }
            )

    private companion object {
        const val WIKIMEDIA_BASE_URL = "https://stream.wikimedia.org"
        const val RECENT_CHANGE_PATH = "/v2/stream/recentchange"
        val INITIAL_RETRY_DELAY: Duration = Duration.ofSeconds(1)
        val MAX_RETRY_DELAY: Duration = Duration.ofMinutes(1)
        const val RETRY_JITTER = 0.5

        val logger = LoggerFactory.getLogger(WikimediaEventStreamAdapter::class.java)
    }

    private fun isRetryable(error: Throwable): Boolean =
        when (error) {
            is WebClientRequestException -> true
            is WebClientResponseException ->
                error.statusCode.is5xxServerError || error.statusCode.value() == 429

            else -> false
        }
}
