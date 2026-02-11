package org.radon.pushup.features.analytics.presentation.dto;

import java.util.UUID;

public record UserTimeLineRequest(
        UUID tenantId,
        String userId
) {
}
