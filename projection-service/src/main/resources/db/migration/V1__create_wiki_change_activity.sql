CREATE TABLE wiki_change_activity (
                                      event_id UUID PRIMARY KEY,
                                      change_type VARCHAR(32) NOT NULL,
                                      page_title TEXT NOT NULL,
                                      wiki VARCHAR(100) NOT NULL,
                                      editor VARCHAR(255) NOT NULL,
                                      occurred_at TIMESTAMPTZ NOT NULL,
                                      consumed_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_wiki_change_activity_occurred_at
    ON wiki_change_activity (occurred_at);