package com.klick.newsevents.adapter.out.kafka

import com.klick.newsevents.application.port.out.WikiChangeSink
import com.klick.newsevents.domain.WikiChange
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import tools.jackson.databind.ObjectMapper

@Component
@Profile("kafka")
class KafkaWikiChangeAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    @Value("\${kafka.topic}") private val topic: String
) : WikiChangeSink {

    override fun accept(change: WikiChange): Mono<Void> =
        Mono.fromCallable { objectMapper.writeValueAsString(change) }
            .flatMap { payload ->
                Mono.fromFuture(kafkaTemplate.send(topic, change.id, payload))
            }
            .then()
}
