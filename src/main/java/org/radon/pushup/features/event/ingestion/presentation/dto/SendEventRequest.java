package org.radon.pushup.features.event.ingestion.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.radon.pushup.features.app.domain.Platform;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record SendEventRequest(
        @NotNull
        @Size(min = 20)
        String eventId,
        String userId,
        @NotBlank
        @NotNull
        String eventName,
        String location,
        String schemaVersion,
        String appVersion,
        String device,
        @NotNull
        Platform platform,
        Long eventTime,
        Map<String, Object> properties
) {
}
