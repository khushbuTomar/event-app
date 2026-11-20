package com.klick.newsevents.domain

import java.time.Instant

data class WikiChange(
    val id: String,
    val type: ChangeType,
    val pageTitle: String,
    val wiki: String,
    val editor: String,
    val occurredAt: Instant
)

enum class ChangeType {
    EDIT,
    NEW,
    LOG,
    CATEGORIZE,
    UNKNOWN
}
