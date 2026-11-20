package com.klick.newsevents.adapter.`in`.startup

import com.klick.newsevents.application.port.`in`.StartWikiChangeIngestion
import jakarta.annotation.PreDestroy
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import reactor.core.Disposable

@Component
class WikiChangeIngestionStartupAdapter(
    private val startWikiChangeIngestion: StartWikiChangeIngestion
) {
    private var subscription: Disposable? = null

    @EventListener(ApplicationReadyEvent::class)
    fun start() {
        subscription = startWikiChangeIngestion.start()
    }

    @PreDestroy
    fun stop() {
        subscription?.dispose()
    }
}
