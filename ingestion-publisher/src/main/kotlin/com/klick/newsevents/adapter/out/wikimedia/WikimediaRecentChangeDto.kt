package com.klick.newsevents.adapter.out.wikimedia

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class WikimediaRecentChangeDto(
    val meta: WikimediaMetaDto,
    val type: String,
    val title: String,
    val wiki: String,
    val user: String,
    val timestamp: Long
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class WikimediaMetaDto(
    val id: String
)
