package com.klick.projectionservice.application.services

import com.klick.projectionservice.application.port.`in`.WikiChangeConsumerPort
import com.klick.projectionservice.application.port.out.WikiChangeActivityRepositoryPort
import com.klick.projectionservice.domain.WikiChangeActivity
import org.springframework.stereotype.Service

@Service
class WikiChangeConsumerService (
    private val activityPort: WikiChangeActivityRepositoryPort
) : WikiChangeConsumerPort {

    override fun consume(activities: List<WikiChangeActivity>) {
        activityPort.saveAll(activities)
    }

}
