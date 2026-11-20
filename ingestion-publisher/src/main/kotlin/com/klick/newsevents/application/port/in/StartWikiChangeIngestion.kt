package com.klick.newsevents.application.port.`in`

import reactor.core.Disposable

interface StartWikiChangeIngestion {
    fun start(): Disposable
}
