package org.radon.pushup.features.analytics.application.port.in;

import org.radon.pushup.features.analytics.presentation.dto.RetentionPointResponse;
import org.radon.pushup.features.analytics.presentation.dto.RetentionRequest;

import java.util.List;

public interface GetRetentionUseCase {
    List<RetentionPointResponse> getRetention(RetentionRequest request);
}
