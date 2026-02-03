package org.radon.pushup.features.event.infrastructure.adapter.out;

import org.radon.pushup.features.event.domain.EventModel;
import org.springframework.kafka.core.KafkaTemplate;

@org.radon.pushup.shared.aop.annotation.EventProducer
public class EventProducer {

    private final KafkaTemplate<String, EventModel> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, EventModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(EventModel model) {
        kafkaTemplate.send("events.raw.v1",model.getTenantId()+":"+model.getAppId()+":"+model.getEventId(), model);
    }


}

