package org.radon.pushup.shared.aop.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic eventTopic() {
        return TopicBuilder.name("events.raw.v1")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic eventTopicDLQ() {
        return TopicBuilder.name("events.raw.dlq.v1")
                .partitions(1)
                .replicas(1)
                .build();
    }


}
