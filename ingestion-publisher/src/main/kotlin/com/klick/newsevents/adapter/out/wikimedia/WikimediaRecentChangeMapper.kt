package com.klick.newsevents.adapter.out.wikimedia

import com.klick.newsevents.domain.ChangeType
import com.klick.newsevents.domain.WikiChange
import java.time.Instant

fun WikimediaRecentChangeDto.toDomain(): WikiChange =
    WikiChange(
        id = meta.id,
        type = type.toChangeType(),
        pageTitle = title,
        wiki = wiki,
        editor = user,
        occurredAt = Instant.ofEpochSecond(timestamp)
    )

private fun String.toChangeType(): ChangeType =
    ChangeType.entries.firstOrNull { changeType ->
        changeType.name.equals(this, ignoreCase = true)
    } ?: ChangeType.UNKNOWN
