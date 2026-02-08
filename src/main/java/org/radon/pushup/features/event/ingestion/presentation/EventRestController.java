package org.radon.pushup.features.event.ingestion.presentation;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.radon.pushup.features.event.ingestion.application.port.in.FetchApiKeyDataUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.IsEventDuplicateUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.SendDlqEventUseCase;
import org.radon.pushup.features.event.ingestion.application.port.in.SendEventUseCase;
import org.radon.pushup.features.event.ingestion.domain.DlqEvent;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.domain.EventStages;
import org.radon.pushup.features.event.ingestion.domain.IngestionErrorCode;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;
import org.radon.pushup.shared.aop.exceptionHandling.ExceptionModel;
import org.radon.pushup.shared.aop.exceptionHandling.model.InvalidEventException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/event")
public class EventRestController {

    private final SendEventUseCase sendEventUseCase;
    private final SendDlqEventUseCase sendDlqEventUseCase;
    private final FetchApiKeyDataUseCase fetchApiKeyDataUseCase;

    public EventRestController(SendEventUseCase sendEventUseCase, SendDlqEventUseCase sendDlqEventUseCase, FetchApiKeyDataUseCase fetchApiKeyDataUseCase) {
        this.sendEventUseCase = sendEventUseCase;
        this.sendDlqEventUseCase = sendDlqEventUseCase;
        this.fetchApiKeyDataUseCase = fetchApiKeyDataUseCase;
    }

    @RequestMapping
    public ResponseEntity<String> sendEvent(@Valid @RequestBody SendEventRequest request, HttpServletRequest httpServletRequest) {

        try{
            var apiKey = httpServletRequest.getHeader("X-API-KEY");

            var eventModel = fetchApiKeyDataUseCase.fetchApiKeyData(apiKey,request);

            sendEventUseCase.sendEvent(eventModel);

            return ResponseEntity.accepted().build();

        } catch (InvalidEventException ex) {

            sendDlqEventUseCase.sendEvent(
                     DlqEvent.builder()
                            .tenantId(ex.getTenantId())
                            .appId(ex.getAppId())
                            .stage(EventStages.INGESTION)
                            .errorCode(IngestionErrorCode.INVALID_SCHEMA.name())
                            .errorMessage(ex.getMessage())
                            .originalEvent(request)
                            .receivedAt(System.currentTimeMillis())
                            .build()
            );

            return ResponseEntity.badRequest().body("Invalid event");

        } catch (ExceptionModel e) {
            throw e;
        } catch (Exception ex) {

            sendDlqEventUseCase.sendEvent(
                    DlqEvent.builder()
                            .stage(EventStages.INGESTION)
                            .errorCode(IngestionErrorCode.INTERNAL_ERROR.name())
                            .errorMessage(ex.getMessage())
                            .originalEvent(request)
                            .receivedAt(System.currentTimeMillis())
                            .build()
            );

            return ResponseEntity.status(500).build();

        }

    }


}
