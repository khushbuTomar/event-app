package com.klick.projectionservice.application.port.`in`

import com.klick.projectionservice.domain.WikiChangeActivity

interface WikiChangeConsumerPort {
    fun consume(activities: List<WikiChangeActivity>)
}
