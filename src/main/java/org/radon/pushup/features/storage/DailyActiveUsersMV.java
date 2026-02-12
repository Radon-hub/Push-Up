package org.radon.pushup.features.storage;

import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(10)
public class DailyActiveUsersMV implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
                CREATE MATERIALIZED VIEW IF NOT EXISTS dau_daily_mv
                TO dau_daily
                AS
                SELECT
                    tenant_id,
                    app_id,
                    event_date,
                    countDistinct(user_id) AS users
                FROM events
                GROUP BY tenant_id,app_id, event_date;
                """;
    }
}
