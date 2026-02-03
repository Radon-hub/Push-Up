package org.radon.pushup.features.event.application.service;

import org.radon.pushup.features.event.application.port.in.FetchApiKeyDataUseCase;
import org.radon.pushup.features.event.application.port.in.SendEventUseCase;
import org.radon.pushup.features.event.application.port.out.EventRepository;
import org.radon.pushup.features.event.domain.EventModel;
import org.radon.pushup.features.event.infrastructure.adapter.out.EventProducer;
import org.radon.pushup.features.event.presentation.dto.SendEventRequest;
import org.radon.pushup.features.event.presentation.mapper.EventMappers;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventService implements SendEventUseCase, FetchApiKeyDataUseCase {

    private final EventProducer eventProducer;
    private final EventRepository eventRepository;

    public EventService(EventProducer eventProducer, EventRepository eventRepository) {
        this.eventProducer = eventProducer;
        this.eventRepository = eventRepository;
    }

    @Override
    public void sendEvent(EventModel model) {
        eventProducer.sendEvent(model);
    }

    @Override
    public EventModel fetchApiKeyData(String apiKey, SendEventRequest request) {
        return eventRepository.fetchApiKeyData(apiKey, request);
    }
}
