package org.radon.pushup.features.analytics.application.port.in;

import org.radon.pushup.features.analytics.presentation.dto.BreakDownRequest;
import org.radon.pushup.features.analytics.presentation.dto.BreakdownPointResponse;

import java.util.List;

public interface GetBreakDownByPropertyUseCase {
    List<BreakdownPointResponse> getBreakDownByProperty(BreakDownRequest request);
}
