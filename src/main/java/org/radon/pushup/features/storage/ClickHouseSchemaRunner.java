package org.radon.pushup.features.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClickHouseSchemaRunner implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final List<ClickHouseEntity> chEntities;

    public ClickHouseSchemaRunner(@Qualifier("clickHouseJdbcTemplate")JdbcTemplate jdbcTemplate, List<ClickHouseEntity> chEntities) {
        this.jdbcTemplate = jdbcTemplate;
        this.chEntities = chEntities;
    }

    @Override
    public void run(String... args) throws Exception {


        chEntities.forEach(it -> jdbcTemplate.execute(it.initialize()));

        applyEventsTTL();
        applyHourlyEventsTTL();
        applyDlqEventsTTL();

        System.out.println("ClickHouse tables initialized");


    }


    /////////////// TTL Sequences

    private void applyEventsTTL() {
        String sql = """
        ALTER TABLE events
        MODIFY TTL event_time + INTERVAL 180 DAY
    """;

        jdbcTemplate.execute(sql);
    }
    private void applyHourlyEventsTTL() {
        String sql = """
        ALTER TABLE events_hourly
        MODIFY TTL event_date + INTERVAL 365 DAY;
    """;

        jdbcTemplate.execute(sql);
    }

    private void applyDlqEventsTTL() {
        String sql = """
        ALTER TABLE events_dlq
        MODIFY TTL received_at + INTERVAL 30 DAY
    """;

        jdbcTemplate.execute(sql);
    }

}
