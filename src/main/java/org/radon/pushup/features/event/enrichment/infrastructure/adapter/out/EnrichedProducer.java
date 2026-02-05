package org.radon.pushup.features.event.enrichment.infrastructure.adapter.out;

import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.shared.aop.annotation.Producer;
import org.springframework.kafka.core.KafkaTemplate;

@Producer
public class EnrichedProducer {

    private final KafkaTemplate<String, EnrichedEvent> kafkaTemplate;

    public EnrichedProducer(KafkaTemplate<String, EnrichedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(EnrichedEvent event) {
        kafkaTemplate.send("events.enriched.v1", event.getTenantId().toString(), event);
    }


}
