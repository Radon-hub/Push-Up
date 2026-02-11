package org.radon.pushup.features.analytics.infrastructure.adapter;

import org.radon.pushup.features.analytics.application.port.out.AnalyticsRepository;
import org.radon.pushup.features.analytics.infrastructure.repository.AnalyticsJDBCRepository;
import org.radon.pushup.features.analytics.presentation.dto.*;
import org.radon.pushup.features.app.domain.Platform;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class AnalyticsRepositoryImp implements AnalyticsRepository {


    private final AnalyticsJDBCRepository analyticsJDBCRepository;

    public AnalyticsRepositoryImp(AnalyticsJDBCRepository analyticsJDBCRepository) {
        this.analyticsJDBCRepository = analyticsJDBCRepository;
    }


    @Override
    public List<BreakdownPointResponse> getBreakDownByColumn(BreakDownRequest request) {
        return analyticsJDBCRepository.breakdownByColumn(
                request.tenantId(),
                request.eventName(),
                request.column(),
                request.startTime(),
                request.endTime()
        );
    }

    @Override
    public List<BreakdownPointResponse> getBreakDownByProperty(BreakDownRequest request) {
        return  analyticsJDBCRepository.breakdownByProperty(
                request.tenantId(),
                request.eventName(),
                request.column(),
                request.startTime(),
                request.endTime()
        );
    }

    @Override
    public long countEvents(TimeEventRequest request) {
        return analyticsJDBCRepository.countEvents(
                request.tenantId(),
                request.eventName(),
                request.startTime(),
                request.endTime()
        );
    }

    @Override
    public List<FunnelStepResponse> getFunnel(FunnelRequest request) {
        return analyticsJDBCRepository.getFunnel(
                request.tenantId(),
                request.startTime(),
                request.endTime(),
                request.steps(),
                request.windowSeconds()
        );
    }

    @Override
    public List<RetentionPointResponse> getRetention(RetentionRequest request) {
        return analyticsJDBCRepository.retention(
                request.tenantId(),
                request.cohortEvent(),
                request.cohortDate(),
                request.days()
        );
    }

    @Override
    public List<TimeSeriesPointResponse> getTimeSeriesPoints(TimeSeriesRequest request) {
        return analyticsJDBCRepository.eventTimeSeries(
                request.tenantId(),
                request.eventName(),
                request.startTime(),
                request.endTime(),
                request.interval()
        );
    }

    @Override
    public List<EventResponse> getUserTimeline(UserTimeLineRequest request) {
        return analyticsJDBCRepository.userTimeline(
                request.tenantId(),
                request.userId()
        );
    }



}
