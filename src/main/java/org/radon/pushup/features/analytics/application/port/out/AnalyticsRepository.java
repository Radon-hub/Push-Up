package org.radon.pushup.features.analytics.application.port.out;

import org.radon.pushup.features.analytics.presentation.dto.*;

import java.util.List;

public interface AnalyticsRepository {
    List<BreakdownPointResponse> getBreakDownByColumn(BreakDownRequest request);
    List<BreakdownPointResponse> getBreakDownByProperty(BreakDownRequest request);
    long countEvents(TimeEventRequest request);
    List<FunnelStepResponse> getFunnel(FunnelRequest request);
    List<RetentionPointResponse> getRetention(RetentionRequest request);
    List<TimeSeriesPointResponse> getTimeSeriesPoints(TimeSeriesRequest timeSeriesRequest);
    List<EventResponse> getUserTimeline(UserTimeLineRequest request);
}
