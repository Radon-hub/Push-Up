package org.radon.pushup.features.event.enrichment.domain;

import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


@Component
public class TimeEnricher implements Enricher{

    @Override
    public EnrichmentStage stage() {
        return EnrichmentStage.DERIVATION;
    }

    @Override
    public EnrichedEvent enrich(EnrichedEvent current, EventModel raw) {

        Instant eventTime = Instant.ofEpochMilli(raw.getEventTime());
        ZonedDateTime zdt = eventTime.atZone(ZoneOffset.UTC);

        return current.toBuilder()
                .eventDate(zdt.toLocalDate().toString())
                .eventHour(zdt.getHour())
                .build();

    }




}
