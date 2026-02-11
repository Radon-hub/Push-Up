package org.radon.pushup.features.storage;

import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(7)
public class HourlyEventsTable implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
                CREATE TABLE IF NOT EXISTS events_hourly
                (
                    tenant_id UUID,
                    app_id UUID,
                    event_date Date,
                    event_hour UInt8,
                    event_name LowCardinality(String),
                    platform LowCardinality(String),
                    count UInt64
                )
                ENGINE = SummingMergeTree
                PARTITION BY (tenant_id, event_date)
                ORDER BY (tenant_id, app_id, event_name, platform, event_date, event_hour);
                """;
    }
}
