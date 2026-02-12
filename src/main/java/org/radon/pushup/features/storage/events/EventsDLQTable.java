package org.radon.pushup.features.storage.events;

import org.radon.pushup.features.storage.ClickHouseEntity;
import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(4)
public class EventsDLQTable implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
            CREATE TABLE IF NOT EXISTS events_dlq
            (
                stage String,
                error_code String,
                error_message String,
                original_event JSON,
                received_at DateTime64(3)
            )
            ENGINE = MergeTree
            PARTITION BY toYYYYMM(received_at)
            ORDER BY received_at
            TTL received_at + INTERVAL 30 DAY
            SETTINGS index_granularity = 8192;                
                """;
    }
}
