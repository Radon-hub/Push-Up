package org.radon.pushup.features.event.enrichment.domain;

import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.springframework.stereotype.Component;

@Component
public class NormalizationEnricher implements Enricher {

    @Override
    public EnrichmentStage stage() {
        return EnrichmentStage.NORMALIZATION;
    }

    @Override
    public EnrichedEvent enrich(EnrichedEvent current, EventModel raw) {

        return current.toBuilder()
                .eventNormalized(current.getEventName().toLowerCase().trim().replace(" ","_"))
                .build();
    }

}
