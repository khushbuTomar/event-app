package com.klick.projectionservice.domain

import java.time.Instant


data class WikiChangeActivity(
    val eventId: String,
    val type: String,
    val pageTitle: String,
    val wiki: String,
    val editor: String,
    val occurredAt: Instant
)