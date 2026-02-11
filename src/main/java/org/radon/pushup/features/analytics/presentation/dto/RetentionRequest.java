package org.radon.pushup.features.analytics.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record RetentionRequest(
        UUID tenantId,
        String cohortEvent,
        Instant cohortDate,
        int days
) {
}
