package org.radon.pushup.features.event.enrichment.application.pipeline;

import lombok.val;
import org.radon.pushup.features.event.enrichment.domain.Enricher;
import org.radon.pushup.features.event.enrichment.domain.EnrichmentStage;
import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.shared.aop.annotation.Pipeline;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Pipeline
public class EnrichmentPipeline {

    private final Map<EnrichmentStage, List<Enricher>> pipeline;

    public EnrichmentPipeline(List<Enricher> enrichers) {
        this.pipeline = enrichers.stream()
                .collect(Collectors.groupingBy(Enricher::stage));
    }

    public EnrichedEvent process(EventModel raw) {

        EnrichedEvent event = EnrichedEvent.builder()
                .tenantId(raw.getTenantId())
                .appId(raw.getAppId())
                .eventId(raw.getEventId())
                .eventName(raw.getEventName())
                .userId(raw.getUserId())
                .eventTime(raw.getEventTime())
                .receivedAt(System.currentTimeMillis())
                .platform(raw.getPlatform())
                .location(raw.getLocation())
                .version("1.0.0")
                .properties(raw.getProperties())
                .build();

        for (EnrichmentStage stage : EnrichmentStage.values()) {
            for (Enricher enricher : pipeline.getOrDefault(stage, List.of())) {
                event = enricher.enrich(event, raw);
            }
        }

        return event;
    }

}
