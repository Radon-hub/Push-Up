package org.radon.pushup.features.event.ingestion.presentation.mapper;

import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;

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
                System.currentTimeMillis(),
                0,
                request.properties()
        );
    }


}
