package org.radon.pushup.features.event.ingestion.application.service;

import org.radon.pushup.features.event.ingestion.application.port.in.FetchApiKeyDataUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.IsEventDuplicateUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.SendDlqEventUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.SendEventUseCase;
import org.radon.pushup.features.event.ingestion.application.port.out.EventRepository;
import org.radon.pushup.features.event.ingestion.domain.DlqEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.domain.validation.EventContextValidator;
import org.radon.pushup.features.event.ingestion.infrastructure.adapter.out.DlqProducer;
import org.radon.pushup.features.event.ingestion.infrastructure.adapter.out.EventProducer;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;
import org.radon.pushup.shared.aop.exceptionHandling.model.AppNotFoundException;
import org.radon.pushup.shared.aop.exceptionHandling.model.DuplicateEventException;
import org.radon.pushup.shared.apiKeys.ApiKeyHasher;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class EventService implements SendEventUseCase, FetchApiKeyDataUseCase, SendDlqEventUseCase {

    private final DlqProducer dlqProducer;
    private final EventProducer eventProducer;
    private final EventRepository eventRepository;
    private final IsEventDuplicateUseCase isDuplicateUseCase;

    public EventService(DlqProducer dlqProducer, EventProducer eventProducer, EventRepository eventRepository, IsEventDuplicateUseCase isDuplicateUseCase) {
        this.dlqProducer = dlqProducer;
        this.eventProducer = eventProducer;
        this.eventRepository = eventRepository;
        this.isDuplicateUseCase = isDuplicateUseCase;
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
    public EventModel fetchApiKeyData(String apiKey,SendEventRequest request) throws NoSuchAlgorithmException {

        var hash = ApiKeyHasher.hash(apiKey);

        var tenant = eventRepository.fetchApiKeyData(hash);

        EventContextValidator.validate(tenant,request);

        var app = tenant.getApps().stream().findFirst().orElseThrow(AppNotFoundException::new);

        if(isDuplicateUseCase.isDuplicate(app.getId(),request.eventId())) {
            throw new DuplicateEventException();
        }

        return new EventModel(
                request.eventId(),
                tenant.getId(),
                app.getId(),
                request.userId() == null ? "unknown" : request.userId(),
                request.eventName(),
                request.location() == null ? "unknown" : request.location(),
                request.schemaVersion() == null ? "unknown" : request.schemaVersion(),
                request.appVersion() == null ? "unknown" : request.appVersion(),
                request.device() == null ? "unknown" : request.device(),
                request.platform(),
                request.eventTime(),
                0,
                request.properties()
        );

    }


}
