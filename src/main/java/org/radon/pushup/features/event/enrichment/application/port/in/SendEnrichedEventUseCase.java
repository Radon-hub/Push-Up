package org.radon.pushup.features.event.enrichment.application.port.in;

import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;

public interface SendEnrichedEventUseCase {
    void sendEnrichedEvent(EventModel raw);
}
