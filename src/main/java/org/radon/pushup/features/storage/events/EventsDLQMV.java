package org.radon.pushup.features.storage.events;

import org.radon.pushup.features.storage.ClickHouseEntity;
import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(6)
public class EventsDLQMV implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
        CREATE MATERIALIZED VIEW IF NOT EXISTS events_dlq_mv
        TO events_dlq
        AS
        SELECT
            JSONExtractString(raw, 'stage') AS stage,
            JSONExtractString(raw, 'errorCode') AS error_code,
            JSONExtractString(raw, 'errorMessage') AS error_message,
            JSONExtractRaw(raw, 'originalEvent') AS original_event,
            parseDateTime64BestEffortOrNull(JSONExtractString(raw, 'receivedAt')) AS received_at
        FROM events_dlq_kafka;                
                """;
    }
}
