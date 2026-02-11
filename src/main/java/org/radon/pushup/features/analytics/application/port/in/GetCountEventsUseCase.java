package org.radon.pushup.features.analytics.application.port.in;

import org.radon.pushup.features.analytics.presentation.dto.TimeEventRequest;

public interface GetCountEventsUseCase {
    long countEvents(TimeEventRequest request);
}
