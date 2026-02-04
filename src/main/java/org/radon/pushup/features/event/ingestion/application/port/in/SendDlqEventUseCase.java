package org.radon.pushup.features.event.ingestion.application.port.in;

import org.radon.pushup.features.event.ingestion.domain.DlqEvent;

public interface SendDlqEventUseCase {
    void sendEvent(DlqEvent dlqEvent);
}
