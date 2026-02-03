package org.radon.pushup.features.event.infrastructure.adapter.in;

import org.radon.pushup.features.event.domain.EventModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@org.radon.pushup.shared.aop.annotation.EventConsumer
public class EventConsumer {

    Logger logger = LoggerFactory.getLogger(EventConsumer.class);


    @KafkaListener(
            topics = "events.raw.v1",
            groupId = "event-raw-group"
    )
    public void receiveUserRegistered(EventModel model) {
        logger.info("------------- Received event! -----------------");
        model.setReceivedAt(System.currentTimeMillis());
        logger.info(
                "\n event id : "+model.getEventId()+ "\n" +
                " event name : "+model.getEventName()+ "\n" +
                " user id : "+model.getUserId()+ "\n" +
                " app id : "+model.getAppId()+ "\n" +
                " tenant id : "+model.getTenantId()+ "\n" +
                " received at : "+model.getReceivedAt()+ "\n" +
                " event time : "+model.getEventTime()+ "\n" +
                " properties : "+model.getProperties()
        );
        logger.info("------------------------- End Of Event Received -------------------");
    }
}
