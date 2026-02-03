package org.radon.pushup.features.event.application.port.out;

import org.radon.pushup.features.event.domain.EventModel;
import org.radon.pushup.features.event.presentation.dto.SendEventRequest;

public interface EventRepository {
    EventModel fetchApiKeyData(String apiKey, SendEventRequest request);
}
