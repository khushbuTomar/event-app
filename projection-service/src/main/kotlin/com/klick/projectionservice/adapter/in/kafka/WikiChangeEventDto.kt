package com.klick.projectionservice.adapter.`in`.kafka

import com.klick.projectionservice.domain.WikiChangeActivity
import java.time.Instant

data class WikiChangeEventDto(
    val id: String,
    val type: String,
    val pageTitle: String,
    val wiki: String,
    val editor: String,
    val occurredAt: Instant
) {
    fun toDomain() = WikiChangeActivity(
        eventId = id,
        type = type,
        pageTitle = pageTitle,
        wiki = wiki,
        editor = editor,
        occurredAt = occurredAt
    )
}
