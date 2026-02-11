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







//
//
//    private final AnalyticsService analyticsService;
//
//    public AnalyticsRestController(AnalyticsService analyticsService) {
//        this.analyticsService = analyticsService;
//    }
//
//    @GetMapping("/events/count")
//    public long count(@RequestParam UUID tenantId,
//                      @RequestParam String eventName,
//                      @RequestParam Instant startTime,
//                      @RequestParam Instant endTime) {
//        return analyticsService.countEvents(tenantId, eventName, startTime, endTime);
//    }
//
//    @GetMapping("/events/timeseries")
//    public List<TimeSeriesPointResponse> timeseries(@RequestParam UUID tenantId,
//                                                    @RequestParam String eventName,
//                                                    @RequestParam Instant startTime,
//                                                    @RequestParam Instant endTime,
//                                                    @RequestParam String interval) {
//        return analyticsService.eventTimeSeries(tenantId, eventName, startTime, endTime, interval);
//    }
//
//    @GetMapping("/users/{userId}/timeline")
//    public List<EventResponse> userTimeline(@PathVariable String userId,
//                                            @RequestParam UUID tenantId) {
//        return analyticsService.userTimeline(tenantId, userId);
//    }
//
//    @GetMapping("/breakdown/column")
//    public List<BreakdownPointResponse> breakdownByColumn(
//            @RequestParam UUID tenantId,
//            @RequestParam String eventName,
//            @RequestParam String column,
//            @RequestParam Instant startTime,
//            @RequestParam Instant endTime
//    ) {
//        return analyticsService.breakdownByColumn(
//                tenantId, eventName, column, startTime, endTime
//        );
//    }
//
//    @GetMapping("/breakdown/property")
//    public List<BreakdownPointResponse> breakdownByProperty(
//            @RequestParam UUID tenantId,
//            @RequestParam String eventName,
//            @RequestParam String property,
//            @RequestParam Instant startTime,
//            @RequestParam Instant endTime
//    ) {
//        return analyticsService.breakdownByProperty(
//                tenantId, eventName, property, startTime, endTime
//        );
//    }
//
//    @GetMapping("/retention")
//    public List<RetentionPointResponse> retention(
//            @RequestParam UUID tenantId,
//            @RequestParam String cohortEvent,
//            @RequestParam Instant cohortDate,
//            @RequestParam(defaultValue = "30") int days
//    ) {
//        return analyticsService.retention(
//                tenantId,
//                cohortEvent,
//                cohortDate,
//                days
//        );
//    }
//
//    @GetMapping("/funnel")
//    public List<FunnelStepResponse> funnel(
//            @RequestParam UUID tenantId,
//            @RequestParam List<String> events,
//            @RequestParam Instant start,
//            @RequestParam Instant end,
//            @RequestParam long windowSeconds
//    ) {
//        return analyticsService.funnel(
//                tenantId,
//                start,
//                end,
//                events,
//                windowSeconds
//        );
//    }

}
