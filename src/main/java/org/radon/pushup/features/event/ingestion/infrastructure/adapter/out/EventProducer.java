package org.radon.pushup.features.event.ingestion.infrastructure.adapter.out;

import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.springframework.kafka.core.KafkaTemplate;
import org.radon.pushup.shared.aop.annotation.Producer;

@Producer
public class EventProducer {

    private final KafkaTemplate<String, EventModel> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, EventModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(EventModel model) {
        kafkaTemplate.send("events.raw.v1",model.getTenantId()+":"+model.getAppId()+":"+model.getEventId(), model);
    }


}

