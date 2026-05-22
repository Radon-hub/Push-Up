package org.radon.pushup.features.storage.events;

import org.radon.pushup.features.storage.ClickHouseEntity;
import org.radon.pushup.shared.aop.annotation.ClickHouseTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

@ClickHouseTable
@Order(2)
public class EventsKafkaTable implements ClickHouseEntity {

    @Value("${app.clickhouse.kafka.kafka_num_consumers}")
    private String kafka_num_consumers;
    @Value("${app.clickhouse.kafka.max_block_size}")
    private String max_block_size;
    @Value("${app.clickhouse.kafka.kafka_poll_timeout_ms}")
    private String kafka_poll_timeout_ms;

    @Override
    public String initialize() {
        return """
            CREATE TABLE IF NOT EXISTS events_kafka
            (
                raw String
            )
            ENGINE = Kafka
            SETTINGS
                kafka_broker_list = 'kafka:29092',
                kafka_topic_list = 'events.enriched.v1',
                kafka_group_name = 'clickhouse-events',
                kafka_format = 'JSONAsString',
                kafka_num_consumers = %s,
                max_block_size = %s,
                kafka_poll_timeout_ms = %s;""".formatted(kafka_num_consumers,max_block_size,kafka_poll_timeout_ms);
    }
}
