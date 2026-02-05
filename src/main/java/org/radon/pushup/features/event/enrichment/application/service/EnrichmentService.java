package org.radon.pushup.features.event.enrichment.application.service;

import org.radon.pushup.features.event.enrichment.application.pipeline.EnrichmentPipeline;
import org.radon.pushup.features.event.enrichment.application.port.in.SendEnrichedEventUseCase;
import org.radon.pushup.features.event.enrichment.domain.model.EnrichedEvent;
import org.radon.pushup.features.event.enrichment.infrastructure.adapter.out.DlqEnrichedProducer;
import org.radon.pushup.features.event.enrichment.infrastructure.adapter.out.EnrichedProducer;
import org.radon.pushup.features.event.ingestion.domain.DlqEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.domain.EventStages;
import org.radon.pushup.features.event.ingestion.infrastructure.adapter.out.DlqProducer;
import org.springframework.stereotype.Service;

@Service
public class EnrichmentService implements SendEnrichedEventUseCase {

    private final EnrichedProducer enrichedProducer;
    private final DlqEnrichedProducer dlqEnrichedProducer;
    private final EnrichmentPipeline enrichmentPipeline;

    public EnrichmentService(EnrichedProducer enrichedProducer, DlqEnrichedProducer dlqEnrichedProducer, EnrichmentPipeline enrichmentPipeline) {
        this.enrichedProducer = enrichedProducer;
        this.dlqEnrichedProducer = dlqEnrichedProducer;
        this.enrichmentPipeline = enrichmentPipeline;
    }


    @Override
    public void sendEnrichedEvent(EventModel raw) {

        try {
            var enriched = enrichmentPipeline.process(raw);
            enrichedProducer.sendEvent(enriched);
        } catch (Exception ex){
            dlqEnrichedProducer.sendEvent(
                    DlqEvent.builder()
                            .stage(EventStages.ENRICHMENT)
                            .errorCode("ENRICHMENT_FAILED")
                            .errorMessage(ex.getMessage())
                            .originalEvent(raw)
                            .receivedAt(System.currentTimeMillis())
                            .build()
            );
        }


    }
}
