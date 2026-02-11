package org.radon.pushup.features.analytics.presentation.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record FunnelRequest(
        UUID tenantId,
        Instant startTime,
        Instant endTime,
        List<String> steps,
        int windowSeconds
) {
}
