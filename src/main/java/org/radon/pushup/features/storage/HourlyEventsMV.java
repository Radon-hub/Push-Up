package org.radon.pushup.features.storage;

import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(8)
public class HourlyEventsMV implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
                CREATE MATERIALIZED VIEW IF NOT EXISTS events_hourly_mv
                TO events_hourly
                AS
                SELECT
                    tenant_id,
                    app_id,
                    event_date,
                    event_hour,
                    event_name,
                    platform,
                    count() AS count
                FROM events
                GROUP BY
                    tenant_id,
                    app_id,
                    event_date,
                    event_hour,
                    event_name,
                    platform;
                """;
    }
}
