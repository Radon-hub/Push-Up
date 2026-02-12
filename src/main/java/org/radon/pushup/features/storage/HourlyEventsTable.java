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
                    bucket_start DateTime64(3),
                    event_name LowCardinality(String),
                    platform LowCardinality(String),
                    location LowCardinality(String),
                    count UInt64
                )
                ENGINE = SummingMergeTree
                PARTITION BY (tenant_id, toDate(bucket_start))
                ORDER BY (tenant_id, app_id, event_name, platform,location, bucket_start)
                TTL bucket_start + INTERVAL 365 DAY
                SETTINGS index_granularity = 8192;
                """;
    }
}
