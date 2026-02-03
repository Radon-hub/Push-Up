package org.radon.pushup.features.event.presentation;


import jakarta.servlet.http.HttpServletRequest;
import org.radon.pushup.features.event.application.port.in.FetchApiKeyDataUseCase;
import org.radon.pushup.features.event.application.port.in.SendEventUseCase;
import org.radon.pushup.features.event.domain.EventModel;
import org.radon.pushup.features.event.infrastructure.adapter.out.EventProducer;
import org.radon.pushup.features.event.presentation.dto.SendEventRequest;
import org.radon.pushup.shared.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/event")
public class EventRestController {

    private final SendEventUseCase sendEventUseCase;
    private final FetchApiKeyDataUseCase fetchApiKeyDataUseCase;

    public EventRestController(SendEventUseCase sendEventUseCase, FetchApiKeyDataUseCase fetchApiKeyDataUseCase) {
        this.sendEventUseCase = sendEventUseCase;
        this.fetchApiKeyDataUseCase = fetchApiKeyDataUseCase;
    }

    @RequestMapping
    public ResponseEntity<Void> sendEvent(@RequestBody SendEventRequest request, HttpServletRequest httpServletRequest) {

        var apiKey = httpServletRequest.getHeader("X-API-KEY");

        var eventModel = fetchApiKeyDataUseCase.fetchApiKeyData(apiKey,request);

        sendEventUseCase.sendEvent(eventModel);

        return ResponseEntity.ok().build();
    }


}
