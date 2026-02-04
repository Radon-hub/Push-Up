package org.radon.pushup.shared.aop.exceptionHandling.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.radon.pushup.features.event.ingestion.application.port.in.SendDlqEventUseCase;
import org.radon.pushup.features.event.ingestion.domain.DlqEvent;
import org.radon.pushup.features.event.ingestion.domain.EventStages;
import org.radon.pushup.features.event.ingestion.domain.IngestionErrorCode;
import org.radon.pushup.features.event.ingestion.presentation.EventRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = EventRestController.class)
public class EventIngestionExceptionHandler {

    private final SendDlqEventUseCase sendDlqEventUseCase;

    public EventIngestionExceptionHandler(SendDlqEventUseCase sendDlqEventUseCase) {
        this.sendDlqEventUseCase = sendDlqEventUseCase;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleUnreadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        Throwable root = rootCause(ex);

        sendDlqEventUseCase.sendEvent(
                DlqEvent.builder()
                        .stage(EventStages.INGESTION)
                        .errorCode(IngestionErrorCode.INVALID_SCHEMA.name())
                        .errorMessage(root.getMessage())
                        .receivedAt(System.currentTimeMillis())
                        .build()
        );

        return ResponseEntity.badRequest().body("Invalid event payload");
    }

    private Throwable rootCause(Throwable ex) {
        while (ex.getCause() != null) ex = ex.getCause();
        return ex;
    }
}
