package org.radon.pushup.features.event.enrichment.infrastructure.adapter.in;

import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.infrastructure.adapter.in.EventConsumer;
import org.radon.pushup.shared.aop.annotation.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

@Consumer
public class EnrichedEventConsumer {

    Logger logger = LoggerFactory.getLogger(EventConsumer.class);


    @KafkaListener(
            topics = "events.enriched.v1",
            groupId = "event-enriched-group"
    )
    public void receiveRawEvents(EnrichedEvent model) {
        logger.info("------------- Received Enriched event! -----------------");
        logger.info(
                "\n event id : "+model.getEventId()+ "\n" +
                        " event name : "+model.getEventName()+ "\n" +
                        " user id : "+model.getUserId()+ "\n" +
                        " app id : "+model.getAppId()+ "\n" +

                        " event date : "+model.getEventDate()+ "\n" +
                        " event hour : "+model.getEventHour()+ "\n" +
                        " event normalized : "+model.getEventNormalized()+ "\n" +
                        " event location : "+model.getLocation()+ "\n" +
                        " event version : "+model.getVersion()+ "\n" +

                        " tenant id : "+model.getTenantId()+ "\n" +
                        " received at : "+model.getReceivedAt()+ "\n" +
                        " event time : "+model.getEventTime()+ "\n" +
                        " properties : "+model.getProperties()
        );
        logger.info("------------------------- End Of Enriched Event Received -------------------");
    }


}
