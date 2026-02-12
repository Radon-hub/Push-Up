package org.radon.pushup.features.storage;

import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(9)
public class DailyActiveUsersTable implements ClickHouseEntity {
    @Override
    public String initialize() {
        return """
                CREATE TABLE IF NOT EXISTS dau_daily
                (
                    tenant_id UUID,
                    app_id UUID,
                    event_date Date,
                    users UInt64
                )
                ENGINE = SummingMergeTree
                PARTITION BY (tenant_id,app_id, event_date)
                ORDER BY (tenant_id,app_id, event_date);
                """;
    }
}
