package org.radon.pushup.features.analytics.presentation.dto;

import lombok.Builder;
import org.radon.pushup.features.app.domain.Platform;

import java.time.Instant;
import java.util.Map;

@Builder
public record EventResponse(
        String tenantId,
        String appId,
        String eventId,
        String userId,
        String eventName,
        String location,
        Platform platform,
        String schemaVersion,
        String appVersion,
        String device,
        Instant eventTime,
        String eventDate,
        int eventHour,
        Map<String,Object> properties
) {
}
