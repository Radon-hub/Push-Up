package org.radon.pushup.features.event.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import org.radon.pushup.features.event.domain.EventModel;
import org.radon.pushup.features.event.presentation.dto.SendEventRequest;

public interface FetchApiKeyDataUseCase {
    EventModel fetchApiKeyData(String apiKey, SendEventRequest request);
}
