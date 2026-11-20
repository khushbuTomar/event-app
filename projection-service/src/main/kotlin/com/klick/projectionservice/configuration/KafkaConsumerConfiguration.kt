package com.klick.projectionservice.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.util.backoff.FixedBackOff

@Configuration
@Profile("kafka")
class KafkaConsumerConfiguration {

    @Bean
    fun kafkaErrorHandler(): CommonErrorHandler =
        DefaultErrorHandler(FixedBackOff(1_000L, FixedBackOff.UNLIMITED_ATTEMPTS))
}
