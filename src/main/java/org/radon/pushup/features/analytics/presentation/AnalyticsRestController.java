package org.radon.pushup.features.analytics.presentation;


import org.radon.pushup.features.analytics.application.port.in.*;
import org.radon.pushup.features.analytics.application.service.AnalyticsService;
import org.radon.pushup.features.analytics.presentation.dto.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("api/v1/analytics")
public class AnalyticsRestController {

    private final GetBreakDownByPropertyUseCase getBreakDownByPropertyUseCase;
    private final GetBreakDownByColumnUseCase getBreakDownByColumnUseCase;
    private final GetCountEventsUseCase getCountEventsUseCase;
    private final GetFunnelUseCase getFunnelUseCase;
    private final GetRetentionUseCase getRetentionUseCase;
    private final GetTimeSeriesUseCase getTimeSeriesUseCase;
    private final GetUserTimeLineUseCase getUserTimeLineUseCase;

    public AnalyticsRestController(GetBreakDownByPropertyUseCase getBreakDownByPropertyUseCase, GetBreakDownByColumnUseCase getBreakDownByColumnUseCase, GetCountEventsUseCase getCountEventsUseCase, GetFunnelUseCase getFunnelUseCase, GetRetentionUseCase getRetentionUseCase, GetTimeSeriesUseCase getTimeSeriesUseCase, GetUserTimeLineUseCase getUserTimeLineUseCase) {
        this.getBreakDownByPropertyUseCase = getBreakDownByPropertyUseCase;
        this.getBreakDownByColumnUseCase = getBreakDownByColumnUseCase;
        this.getCountEventsUseCase = getCountEventsUseCase;
        this.getFunnelUseCase = getFunnelUseCase;
        this.getRetentionUseCase = getRetentionUseCase;
        this.getTimeSeriesUseCase = getTimeSeriesUseCase;
        this.getUserTimeLineUseCase = getUserTimeLineUseCase;
    }

    @GetMapping("/events/count")
    public long count(@RequestBody TimeEventRequest timeEventRequest) {
        return getCountEventsUseCase.countEvents(timeEventRequest);
    }

    @GetMapping("/events/timeseries")
    public List<TimeSeriesPointResponse> timeseries(@RequestBody TimeSeriesRequest timeSeriesRequest) {
        return getTimeSeriesUseCase.getTimeSeriesPoints(timeSeriesRequest);
    }

    @GetMapping("/users/{userId}/timeline")
    public List<EventResponse> userTimeline(@PathVariable String userId,
                                            @RequestParam("tenant_id") UUID tenantId) {
        return getUserTimeLineUseCase.getUserTimeline(new UserTimeLineRequest(tenantId, userId));
    }

    @GetMapping("/breakdown/column")
    public List<BreakdownPointResponse> breakdownByColumn(
            @RequestBody BreakDownRequest breakDownRequest
    ) {
        return getBreakDownByColumnUseCase.getBreakDownByColumn(breakDownRequest);
    }

    @GetMapping("/breakdown/property")
    public List<BreakdownPointResponse> breakdownByProperty(@RequestBody BreakDownRequest breakDownRequest) {
        return getBreakDownByPropertyUseCase.getBreakDownByProperty(breakDownRequest);
    }

    @GetMapping("/retention")
    public List<RetentionPointResponse> retention(@RequestBody RetentionRequest retentionRequest) {
        return getRetentionUseCase.getRetention(retentionRequest);
    }

    @GetMapping("/funnel")
    public List<FunnelStepResponse> funnel(@RequestBody FunnelRequest funnelRequest) {
        return getFunnelUseCase.getFunnel(funnelRequest);
    }


}
