package org.radon.pushup.features.event.application.port.in;

import java.util.UUID;

public interface IsEventDuplicateUseCase {
    Boolean isDuplicate(UUID appId, String eventId);
}
