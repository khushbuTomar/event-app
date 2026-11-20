package com.klick.newsevents.adapter.out.logging

import com.klick.newsevents.application.port.out.WikiChangeSink
import com.klick.newsevents.domain.WikiChange
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

private val logger = LoggerFactory.getLogger(LoggingWikiChangeAdapter::class.java)

@Component
@Profile("!kafka")
class LoggingWikiChangeAdapter : WikiChangeSink {

    override fun accept(change: WikiChange): Mono<Void> {
        return Mono.fromRunnable {
            logger.info("Received WikiChange: {}", change)
        }
    }
}
