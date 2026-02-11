package org.radon.pushup.features.storage.events;

import org.radon.pushup.features.storage.ClickHouseEntity;
import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(3)
public class EventsMV implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
        CREATE MATERIALIZED VIEW IF NOT EXISTS events_mv
        TO events
        AS
        SELECT
            toUUID(JSONExtractString(raw, 'tenantId')) AS tenant_id,
            toUUID(JSONExtractString(raw, 'appId')) AS app_id,
            JSONExtractString(raw, 'eventId') AS event_id,
            JSONExtractString(raw, 'userId') AS user_id,
            JSONExtractString(raw, 'eventName') AS event_name,
            JSONExtractString(raw, 'eventNormalized') AS event_normalized,
            JSONExtractString(raw, 'schemaVersion') AS schema_version,
            JSONExtractString(raw, 'appVersion') AS app_version,
            JSONExtractString(raw, 'device') AS device,
            JSONExtractString(raw, 'location') AS location,
            JSONExtractString(raw, 'platform') AS platform,
            parseDateTime64BestEffortOrNull(JSONExtractString(raw, 'eventTime')) AS event_time,
            toDate(JSONExtractString(raw, 'eventDate')) AS event_date,
            JSONExtractUInt(raw, 'eventHour') AS event_hour,
            parseDateTime64BestEffortOrNull(JSONExtractString(raw, 'receivedAt')) AS received_at,
            JSONExtractRaw(raw, 'properties') AS properties
        FROM events_kafka;
        """;
    }
}
