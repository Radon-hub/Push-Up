package org.radon.pushup.features.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClickHouseSchemaRunner implements CommandLineRunner {


    private final JdbcTemplate jdbcTemplate;

    public ClickHouseSchemaRunner(@Qualifier("clickHouseJdbcTemplate")JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        createEventsTable();
        applyEventsTTL();

        createEventsKafkaTable();
        createEventsMaterializedView();

        createDLQEventsTable();
        applyDlqEventsTTL();

        createDLQEventsKafkaTable();
        createDLQEventsMaterializedView();

        System.out.println("ClickHouse tables initialized");
    }

    private void createEventsTable() {
        String sql = """
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

        jdbcTemplate.execute(sql);
    }

    private void createEventsKafkaTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS events_kafka
            (
                raw String
            )
            ENGINE = Kafka
            SETTINGS
                kafka_broker_list = 'kafka:29092',
                kafka_topic_list = 'events.enriched.v1',
                kafka_group_name = 'clickhouse-events',
                kafka_format = 'JSONAsString';
            """;

        jdbcTemplate.execute(sql);
    }

    private void createEventsMaterializedView() {
        String sql = """
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

        jdbcTemplate.execute(sql);
    }



    /////////////// DLQ EVENTS

    private void createDLQEventsTable() {
        String sql = """
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
            SETTINGS index_granularity = 8192;
            """;

        jdbcTemplate.execute(sql);
    }


    private void createDLQEventsKafkaTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS events_dlq_kafka
            (
                raw String
            )
            ENGINE = Kafka
            SETTINGS
                kafka_broker_list = 'kafka:29092',
                kafka_topic_list = 'events.enriched.dlq.v1,events.raw.dlq.v1',
                kafka_group_name = 'clickhouse-events-dlq',
                kafka_format = 'JSONAsString';
            """;

        jdbcTemplate.execute(sql);
    }

    private void createDLQEventsMaterializedView() {
        String sql = """   
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

        jdbcTemplate.execute(sql);
    }

    private void applyEventsTTL() {
        String sql = """
        ALTER TABLE events
        MODIFY TTL event_time + INTERVAL 180 DAY
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
