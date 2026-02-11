package org.radon.pushup.features.analytics.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record BreakDownRequest(
        UUID tenantId,
        String eventName,
        String column,
        Instant startTime,
        Instant endTime
) {
}
