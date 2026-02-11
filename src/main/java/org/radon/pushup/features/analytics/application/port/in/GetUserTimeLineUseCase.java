package org.radon.pushup.features.analytics.application.port.in;

import org.radon.pushup.features.analytics.presentation.dto.EventResponse;
import org.radon.pushup.features.analytics.presentation.dto.UserTimeLineRequest;

import java.util.List;

public interface GetUserTimeLineUseCase {
    List<EventResponse> getUserTimeline(UserTimeLineRequest request);
}
