package com.klick.newsevents.application.port.out

import com.klick.newsevents.domain.WikiChange
import reactor.core.publisher.Flux

interface WikiChangeSource {
    fun streamChanges(): Flux<WikiChange>
}
