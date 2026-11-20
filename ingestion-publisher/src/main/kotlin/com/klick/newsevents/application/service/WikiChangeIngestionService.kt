package com.klick.newsevents.application.service

import com.klick.newsevents.application.port.`in`.StartWikiChangeIngestion
import com.klick.newsevents.application.port.out.WikiChangeSink
import com.klick.newsevents.application.port.out.WikiChangeSource
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.Disposable

@Service
class WikiChangeIngestionService(
    private val source: WikiChangeSource,
    private val sink: WikiChangeSink
) : StartWikiChangeIngestion {

    override fun start(): Disposable =
        source.streamChanges()
            .concatMap(sink::accept)
            .subscribe(
                {},
                { error -> logger.error("Wiki change ingestion stopped", error) }
            )

    private companion object {
        val logger = LoggerFactory.getLogger(WikiChangeIngestionService::class.java)
    }
}
