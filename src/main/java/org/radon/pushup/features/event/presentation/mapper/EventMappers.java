package org.radon.pushup.features.event.presentation.mapper;

import org.radon.pushup.features.event.domain.EventModel;
import org.radon.pushup.features.event.presentation.dto.SendEventRequest;

import java.time.Instant;
import java.util.UUID;

public class EventMappers {

    public static EventModel fromEventRequestToEventModel(
            SendEventRequest request,
            UUID tenantId,
            UUID appId
    ){
        return new EventModel(
                request.eventId(),
                tenantId,
                appId,
                request.userId(),
                request.eventName(),
                request.location(),
                request.platform(),
                Instant.now().getEpochSecond(),
                0,
                request.properties()
        );
    }


}
