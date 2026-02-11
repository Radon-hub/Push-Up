package org.radon.pushup.features.analytics.application.service;

import org.radon.pushup.features.analytics.application.port.in.*;
import org.radon.pushup.features.analytics.application.port.out.AnalyticsRepository;
import org.radon.pushup.features.analytics.presentation.dto.*;
import org.radon.pushup.features.app.domain.Platform;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
public class AnalyticsService implements GetBreakDownByPropertyUseCase, GetBreakDownByColumnUseCase, GetCountEventsUseCase, GetFunnelUseCase, GetRetentionUseCase,GetTimeSeriesUseCase,GetUserTimeLineUseCase {

    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    @Override
    public List<BreakdownPointResponse> getBreakDownByColumn(BreakDownRequest request) {
        return analyticsRepository.getBreakDownByColumn(request);
    }

    @Override
    public List<BreakdownPointResponse> getBreakDownByProperty(BreakDownRequest request) {
        return analyticsRepository.getBreakDownByProperty(request);
    }

    @Override
    public long countEvents(TimeEventRequest request) {
        return analyticsRepository.countEvents(request);
    }

    @Override
    public List<FunnelStepResponse> getFunnel(FunnelRequest request) {
        return analyticsRepository.getFunnel(request);
    }

    @Override
    public List<RetentionPointResponse> getRetention(RetentionRequest request) {
        return analyticsRepository.getRetention(request);
    }

    @Override
    public List<TimeSeriesPointResponse> getTimeSeriesPoints(TimeSeriesRequest timeSeriesRequest) {
        return analyticsRepository.getTimeSeriesPoints(timeSeriesRequest);
    }

    @Override
    public List<EventResponse> getUserTimeline(UserTimeLineRequest request) {
        return analyticsRepository.getUserTimeline(request);
    }
}

//@Service
//public class AnalyticsService {
//
//    private final JdbcTemplate jdbcTemplate;
//    private final ObjectMapper objectMapper;
//
//    public AnalyticsService(@Qualifier("clickHouseJdbcTemplate")JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.objectMapper = objectMapper;
//    }
//
//    public long countEvents(UUID tenantId, String eventName, Instant start, Instant end) {
//        String sql = "SELECT count() FROM events WHERE tenant_id = ? AND event_name = ? AND event_time BETWEEN ? AND ?";
//        return jdbcTemplate.queryForObject(sql, Long.class, tenantId, eventName, start, end);
//    }
//
//    public List<TimeSeriesPointResponse> eventTimeSeries(UUID tenantId, String eventName,
//                                                         Instant start, Instant end,
//                                                         String interval) {
//        String timeFunc = interval.equals("hour") ? "toStartOfHour(event_time)" : "toStartOfDay(event_time)";
//        String sql = String.format("SELECT %s AS ts, count() AS cnt FROM events WHERE tenant_id = ? AND event_name = ? AND event_time BETWEEN ? AND ? GROUP BY ts ORDER BY ts", timeFunc);
//        return jdbcTemplate.query(sql,
//                new Object[]{tenantId, eventName, start, end},
//                (rs, rowNum) -> new TimeSeriesPointResponse(rs.getTimestamp("ts").toInstant(), rs.getLong("cnt")));
//    }
//
//    public List<EventResponse> userTimeline(UUID tenantId, String userId) {
//        String sql = "SELECT events.tenant_id,events.app_id,events.user_id,events.event_id,events.event_name,events.location,events.platform,events.schema_version,events.app_version,events.device,events.event_time,events.event_date,events.event_hour,events.received_at,toJSONString(events.properties) as properties FROM events WHERE tenant_id = ? AND user_id = ? ORDER BY event_time ASC";
//        return jdbcTemplate.query(sql,
//                new Object[]{tenantId, userId},
//                (rs, rowNum) -> mapRowToEventResponse(rs));
//    }
//
//    private EventResponse mapRowToEventResponse(ResultSet rs) throws SQLException {
//
//        Map<String, Object> properties;
//
//        try {
//            properties = objectMapper.readValue(
//                    rs.getString("properties"),
//                    new TypeReference<Map<String, Object>>() {}
//            );
//        } catch (Exception e) {
//            properties = Map.of(); // safety fallback
//        }
//
//        return EventResponse.builder()
//                .tenantId(rs.getString("tenant_id"))
//                .appId(rs.getString("app_id"))
//                .eventId(rs.getString("event_id"))
//                .userId(rs.getString("user_id"))
//                .eventName(rs.getString("event_name"))
//                .location(rs.getString("location"))
//                .platform(Platform.valueOf(rs.getString("platform")))
//                .schemaVersion(rs.getString("schema_version"))
//                .appVersion(rs.getString("app_version"))
//                .device(rs.getString("device"))
//                .eventTime(rs.getTimestamp("event_time").toInstant())
//                .eventDate(String.valueOf(rs.getDate("event_date").toLocalDate()))
//                .eventHour(rs.getInt("event_hour"))
//                .properties(properties)
//                .build();
//    }
//
////  TODO : FUNNEL STATICS
//
////    public List<FunnelStep> funnel(UUID tenantId, Instant start, Instant end, List<String> steps, long windowSeconds) {
////
////        String sql = """
////        SELECT
////            countIf(funnel_step = 1) AS step_1,
////            countIf(funnel_step = 2) AS step_2
////        FROM (
////            SELECT user_id,
////                   windowFunnel(10)(
////                       CAST(event_time AS DateTime),
////                       event_name = 'LOGIN',
////                       event_name = 'LOG_OUT'
////                   ) AS funnel_step
////            FROM events
////            WHERE tenant_id = ?
////              AND event_time BETWEEN ? AND ?
////            GROUP BY user_id
////        )
////        """;
////
////        Map<String, Object> result = jdbcTemplate.queryForMap(sql, tenantId, start, end);
////
////        List<FunnelStep> funnelSteps = new ArrayList<>();
////        funnelSteps.add(new FunnelStep(1, ((Number) result.get("step_1")).longValue()));
////        funnelSteps.add(new FunnelStep(2, ((Number) result.get("step_2")).longValue()));
////
////        return funnelSteps;
////    }
//
//
//    public List<FunnelStepResponse> funnel(
//            UUID tenantId,
//            Instant start,
//            Instant end,
//            List<String> steps,
//            long windowSeconds
//    ) {
//        if (steps == null || steps.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//// windowFunnel first argument must be timestamp
//        String funnelConditions = "CAST(event_time AS DateTime), " +
//                steps.stream()
//                        .map(e -> "event_name = '" + e + "'")
//                        .collect(Collectors.joining(", "));
//        // 2️⃣ Build countIf columns dynamically for each step
//        String countIfColumns = IntStream.range(0, steps.size())
//                .mapToObj(i -> "countIf(funnel_step = " + (i + 1) + ") AS step_" + (i + 1))
//                .collect(Collectors.joining(", "));
//
//        // 3️⃣ Build the SQL
//        String sql = """
//        SELECT %s
//        FROM (
//            SELECT user_id,
//                   windowFunnel(%d)(%s) AS funnel_step
//            FROM events
//            WHERE tenant_id = ?
//              AND event_time BETWEEN ? AND ?
//            GROUP BY user_id
//        )
//        """.formatted(countIfColumns, windowSeconds, funnelConditions);
//
//        // 4️⃣ Execute query
//        Map<String, Object> result = jdbcTemplate.queryForMap(sql, tenantId, start, end);
//
//        // 5️⃣ Map results to FunnelStep objects
//        List<FunnelStepResponse> funnelStepResponses = new ArrayList<>();
//        for (int i = 0; i < steps.size(); i++) {
//            funnelStepResponses.add(new FunnelStepResponse(
//                    i + 1,
//                    ((Number) result.get("step_" + (i + 1))).longValue()
//            ));
//        }
//
//        return funnelStepResponses;
//    }
//
//
//
//    public List<RetentionPointResponse> retention(UUID tenantId,
//                                                  String cohortEvent,
//                                                  Instant cohortDate,
//                                                  int days) {
//
//        String sql = """
//            WITH cohort AS (
//                SELECT DISTINCT user_id
//                FROM events
//                WHERE tenant_id = ?
//                  AND event_name = ?
//                  AND event_date = ?
//            )
//            SELECT
//                dateDiff('day', ?, event_date) AS day,
//                countDistinct(e.user_id) AS users
//            FROM events e
//            INNER JOIN cohort c ON e.user_id = c.user_id
//            WHERE tenant_id = ?
//              AND event_date BETWEEN ? AND ?
//            GROUP BY day
//            ORDER BY day
//            """;
//
//        return jdbcTemplate.query(
//                sql,
//                new Object[]{
//                        tenantId,
//                        cohortEvent,
//                        cohortDate,
//                        cohortDate,
//                        tenantId,
//                        cohortDate,
//                        cohortDate.plus(days, ChronoUnit.DAYS)
//                },
//                (rs, i) -> new RetentionPointResponse(
//                        rs.getInt("day"),
//                        rs.getLong("users")
//                )
//        );
//    }
//
//    public List<BreakdownPointResponse> breakdownByColumn(UUID tenantId,
//                                                          String eventName,
//                                                          String column,
//                                                          Instant start,
//                                                          Instant end) {
//
//        // Guardrail: allow only safe columns
//        Set<String> allowedColumns = Set.of("platform", "location", "version");
//
//        if (!allowedColumns.contains(column)) {
//            throw new IllegalArgumentException("Unsupported breakdown column");
//        }
//
//        String sql = """
//        SELECT %s AS key, count() AS cnt
//        FROM events
//        WHERE tenant_id = ?
//          AND event_name = ?
//          AND event_time BETWEEN ? AND ?
//        GROUP BY key
//        ORDER BY cnt DESC
//        """.formatted(column);
//
//        return jdbcTemplate.query(
//                sql,
//                new Object[]{tenantId, eventName, start, end},
//                (rs, i) -> new BreakdownPointResponse(
//                        rs.getString("key"),
//                        rs.getLong("cnt")
//                )
//        );
//    }
//
//    public List<BreakdownPointResponse> breakdownByProperty(UUID tenantId,
//                                                            String eventName,
//                                                            String property,
//                                                            Instant start,
//                                                            Instant end) {
//
//        // Validate property name to prevent SQL injection
//        if (!property.matches("[a-zA-Z0-9_]+")) {
//            throw new IllegalArgumentException("Invalid property name");
//        }
//
//        String sql = """
//        SELECT
//            JSONExtractString(toJSONString(properties), '%s') AS key,
//            count() AS cnt
//        FROM events
//        WHERE tenant_id = ?
//          AND event_name = ?
//          AND event_time BETWEEN ? AND ?
//        GROUP BY key
//        ORDER BY cnt DESC
//        """.formatted(property);
//
//        return jdbcTemplate.query(
//                sql,
//                new Object[]{tenantId, eventName, start, end},
//                (rs, i) -> new BreakdownPointResponse(
//                        rs.getString("key"),
//                        rs.getLong("cnt")
//                )
//        );
//    }
//
//
//
//
//}
