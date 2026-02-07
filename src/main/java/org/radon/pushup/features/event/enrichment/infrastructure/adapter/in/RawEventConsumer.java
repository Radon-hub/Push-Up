package org.radon.pushup.features.event.enrichment.infrastructure.adapter.in;

import org.radon.pushup.features.event.enrichment.application.port.in.SendEnrichedEventUseCase;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.shared.aop.annotation.Consumer;
import org.springframework.kafka.annotation.KafkaListener;

@Consumer
public class RawEventConsumer {

    private final SendEnrichedEventUseCase sendEnrichedEventUseCase;

    public RawEventConsumer(SendEnrichedEventUseCase sendEnrichedEventUseCase) {
        this.sendEnrichedEventUseCase = sendEnrichedEventUseCase;
    }

    @KafkaListener(
            topics = "events.raw.v1",
            groupId = "event-enrichment-group",
            containerFactory = "eventModelKafkaFactory"
    )
    public void receiveRawEvents(EventModel rawEvent) {
        sendEnrichedEventUseCase.sendEnrichedEvent(rawEvent);
    }
}
