package org.radon.pushup.features.event.ingestion.application.port.in;

import java.util.UUID;

public interface IsEventDuplicateUseCase {
    Boolean isDuplicate(UUID appId, String eventId);
}
