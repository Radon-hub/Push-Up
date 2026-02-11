package org.radon.pushup.features.analytics.application.port.in;

import org.radon.pushup.features.analytics.presentation.dto.TimeSeriesPointResponse;
import org.radon.pushup.features.analytics.presentation.dto.TimeSeriesRequest;

import java.util.List;

public interface GetTimeSeriesUseCase {
    List<TimeSeriesPointResponse> getTimeSeriesPoints(TimeSeriesRequest timeSeriesRequest);
}
