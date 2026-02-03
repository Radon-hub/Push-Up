package org.radon.pushup.features.event.presentation.dto;

import org.radon.pushup.features.app.domain.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record SendEventRequest(
        String eventId,
        String userId,
        String eventName,
        String location,
        Platform platform,
        Map<String, Object> properties
) {
}
