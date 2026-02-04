package org.radon.pushup.features.event.ingestion.application.service;

import org.radon.pushup.features.event.ingestion.application.port.in.FetchApiKeyDataUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.SendDlqEventUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.SendEventUseCase;
import org.radon.pushup.features.event.ingestion.application.port.out.EventRepository;
import org.radon.pushup.features.event.ingestion.domain.DlqEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.infrastructure.adapter.out.DlqProducer;
import org.radon.pushup.features.event.ingestion.infrastructure.adapter.out.EventProducer;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class EventService implements SendEventUseCase, FetchApiKeyDataUseCase, SendDlqEventUseCase {

    private final DlqProducer dlqProducer;
    private final EventProducer eventProducer;
    private final EventRepository eventRepository;

    public EventService(DlqProducer dlqProducer, EventProducer eventProducer, EventRepository eventRepository) {
        this.dlqProducer = dlqProducer;
        this.eventProducer = eventProducer;
        this.eventRepository = eventRepository;
    }

    @Override
    public void sendEvent(EventModel model) {
        eventProducer.sendEvent(model);
    }

    @Override
    public void sendEvent(DlqEvent dlqEvent) {
        dlqProducer.sendEvent(dlqEvent);
    }

    @Override
    public EventModel fetchApiKeyData(String apiKey, SendEventRequest request) throws NoSuchAlgorithmException {
        return eventRepository.fetchApiKeyData(apiKey, request);
    }


}
