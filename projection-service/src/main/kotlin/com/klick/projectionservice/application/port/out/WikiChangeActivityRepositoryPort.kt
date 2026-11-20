package com.klick.projectionservice.application.port.out

import com.klick.projectionservice.domain.WikiChangeActivity

interface WikiChangeActivityRepositoryPort {

    fun saveAll(activities: List<WikiChangeActivity>)

}
