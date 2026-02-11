package org.radon.pushup.features.analytics.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record TimeSeriesRequest(
        UUID tenantId,
        String eventName,
        Instant startTime,
        Instant endTime,
        String interval
) {
}
