package org.radon.pushup.features.storage.events;

import org.radon.pushup.features.storage.ClickHouseEntity;
import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(1)
public class EventsTable implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
            CREATE TABLE IF NOT EXISTS events
            (
                tenant_id UUID,
                app_id UUID,
                event_id String,
                user_id String,
                event_name LowCardinality(String),
                event_normalized LowCardinality(String),
                location String,
                platform String,
                schema_version String,
                app_version String,
                device String,
                event_time DateTime64(3),
                event_date Date,
                event_hour UInt8,
                received_at DateTime64(3),
                properties JSON
            )
            ENGINE = MergeTree
            PARTITION BY (tenant_id, toYYYYMM(event_date))
            ORDER BY (tenant_id, app_id, event_name, event_time, event_id)
            SETTINGS index_granularity = 8192;    
            """;
    }
}
