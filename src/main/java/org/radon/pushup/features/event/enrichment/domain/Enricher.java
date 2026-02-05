package org.radon.pushup.features.event.enrichment.domain;

import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;

public interface Enricher {

    EnrichmentStage stage();
    EnrichedEvent enrich(EnrichedEvent current, EventModel raw);
}
