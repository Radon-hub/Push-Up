package org.radon.pushup.features.analytics.application.port.in;

import org.radon.pushup.features.analytics.presentation.dto.FunnelRequest;
import org.radon.pushup.features.analytics.presentation.dto.FunnelStepResponse;

import java.util.List;

public interface GetFunnelUseCase {
    List<FunnelStepResponse> getFunnel(FunnelRequest request);
}
