package org.radon.pushup.features.event.ingestion.application.port.out;

import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;

import java.security.NoSuchAlgorithmException;

public interface EventRepository {
    EventModel fetchApiKeyData(String apiKey, SendEventRequest request) throws NoSuchAlgorithmException;
}
