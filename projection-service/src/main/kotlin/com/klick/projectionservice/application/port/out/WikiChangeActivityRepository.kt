package com.klick.projectionservice.application.port.out

import com.klick.projectionservice.domain.WikiChangeActivity
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Repository
import java.sql.Timestamp
import java.util.UUID

@Repository
class WikiChangeActivityRepository(
    private val jdbi: Jdbi
): WikiChangeActivityRepositoryPort {

    override fun saveAll(activities: List<WikiChangeActivity>) {
        if (activities.isEmpty()) return

        jdbi.useTransaction<Exception> { handle ->
            val batch = handle.prepareBatch(saveQuery)

            activities.forEach { activity ->
                batch
                    .bind("eventId", UUID.fromString(activity.eventId))
                    .bind("type", activity.type)
                    .bind("pageTitle", activity.pageTitle)
                    .bind("wiki", activity.wiki)
                    .bind("editor", activity.editor)
                    .bind("occurredAt", Timestamp.from(activity.occurredAt))
                    .add()
            }

            batch.execute()
        }
    }

    private companion object {
        val saveQuery =
            """
                INSERT INTO wiki_change_activity (
                    event_id,
                    change_type,
                    page_title,
                    wiki,
                    editor,
                    occurred_at
                )
                VALUES (
                    :eventId,
                    :type,
                    :pageTitle,
                    :wiki,
                    :editor,
                    :occurredAt
                )
                ON CONFLICT (event_id) DO NOTHING
            """.trimIndent()
    }
}
