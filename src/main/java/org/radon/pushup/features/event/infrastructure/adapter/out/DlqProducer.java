package org.radon.pushup.features.event.infrastructure.adapter.out;


import org.radon.pushup.features.event.domain.DlqEvent;
import org.radon.pushup.shared.aop.annotation.Producer;
import org.springframework.kafka.core.KafkaTemplate;

@Producer
public class DlqProducer {

    private final KafkaTemplate<String, DlqEvent> kafkaTemplate;

    public DlqProducer(KafkaTemplate<String, DlqEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(DlqEvent event) {
        kafkaTemplate.send(
                "events.raw.dlq.v1",
                event.getTenantId(),
                event
        );
    }
}