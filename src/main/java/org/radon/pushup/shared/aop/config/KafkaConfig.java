package org.radon.pushup.shared.aop.config;


import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@EnableKafka
public class KafkaConfig {


    @Value("${app.kafka.partitions}")
    private int kafka_partitions;
    @Value("${app.kafka.replicas}")
    private int kafka_replicas;

    @Bean
    public NewTopic eventTopic() {
        return TopicBuilder.name("events.raw.v1")
                .partitions(kafka_partitions)
                .replicas(kafka_replicas)
                .build();
    }

    @Bean
    public NewTopic eventTopicDLQ() {
        return TopicBuilder.name("events.raw.dlq.v1")
                .partitions(kafka_partitions)
                .replicas(kafka_replicas)
                .build();
    }

    @Bean
    public NewTopic enrichedEventTopic() {
        return TopicBuilder.name("events.enriched.v1")
                .partitions(kafka_partitions)
                .replicas(kafka_replicas)
                .build();
    }

    @Bean
    public NewTopic enrichedEventTopicDLQ() {
        return TopicBuilder.name("events.enriched.dlq.v1")
                .partitions(kafka_partitions)
                .replicas(kafka_replicas)
                .build();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventModel> eventModelKafkaFactory(ConsumerFactory<String, EventModel> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, EventModel> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }



    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EnrichedEvent> enrichedEventKafkaFactory(ConsumerFactory<String, EnrichedEvent> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, EnrichedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

}
