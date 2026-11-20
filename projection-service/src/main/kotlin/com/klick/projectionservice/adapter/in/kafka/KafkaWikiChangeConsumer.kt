package com.klick.projectionservice.adapter.`in`.kafka

import com.klick.projectionservice.application.port.`in`.WikiChangeConsumerPort
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component
import tools.jackson.databind.ObjectMapper

@Component
@Profile("kafka")
class KafkaWikiChangeConsumer(
    private val wikiChangeConsumerPort: WikiChangeConsumerPort,
    private val objectMapper: ObjectMapper
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["\${kafka.topic}"],
        groupId = "\${spring.kafka.consumer.group-id}"
    )
    fun consume(records: List<ConsumerRecord<String, String>>, acknowledgment: Acknowledgment) {
        if (records.isEmpty()) return

        val activities = records.map { record ->
            objectMapper.readValue(record.value(), WikiChangeEventDto::class.java).toDomain()
        }

        wikiChangeConsumerPort.consume(activities)
        acknowledgment.acknowledge()

        logger.info(
            "Committed Kafka batch | size={} | offsets={}",
            records.size,
            records.groupBy { it.partition() }.mapValues { (_, values) ->
                "${values.first().offset()}-${values.last().offset()}"
            }
        )
    }
}
