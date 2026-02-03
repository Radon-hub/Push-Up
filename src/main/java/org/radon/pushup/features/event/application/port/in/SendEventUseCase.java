package org.radon.pushup.features.event.application.port.in;

import org.radon.pushup.features.event.domain.EventModel;
import org.radon.pushup.features.event.presentation.dto.SendEventRequest;

import java.util.UUID;

public interface SendEventUseCase {
    void sendEvent(EventModel model);
}
