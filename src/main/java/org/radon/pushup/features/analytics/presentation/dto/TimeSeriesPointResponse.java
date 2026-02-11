package org.radon.pushup.features.analytics.presentation.dto;

import java.time.Instant;

public record TimeSeriesPointResponse(
        Instant timestamp,
        long count
) {
}
