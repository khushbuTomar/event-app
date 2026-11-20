package com.klick.newsevents.application.port.out

import com.klick.newsevents.domain.WikiChange
import reactor.core.publisher.Mono

interface WikiChangeSink {
    fun accept(change: WikiChange) : Mono<Void>
}
