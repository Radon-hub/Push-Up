package org.radon.pushup.features.event.ingestion.application.port.in;

import org.radon.pushup.features.event.ingestion.domain.EventModel;

public interface SendEventUseCase {
    void sendEvent(EventModel model);
}
