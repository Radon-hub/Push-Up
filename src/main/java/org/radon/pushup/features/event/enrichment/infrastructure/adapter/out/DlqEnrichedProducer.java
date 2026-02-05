package org.radon.pushup.features.event.enrichment.infrastructure.adapter.out;

import org.radon.pushup.features.event.ingestion.domain.DlqEvent;
import org.radon.pushup.shared.aop.annotation.Producer;
import org.springframework.kafka.core.KafkaTemplate;

@Producer
public class DlqEnrichedProducer {

    private final KafkaTemplate<String, DlqEvent> kafkaTemplate;

    public DlqEnrichedProducer(KafkaTemplate<String, DlqEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(DlqEvent event) {
        kafkaTemplate.send(
                "events.enriched.dlq.v1",
                event.getTenantId(),
                event
        );
    }

}
