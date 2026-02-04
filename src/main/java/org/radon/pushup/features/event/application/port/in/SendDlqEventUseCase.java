package org.radon.pushup.features.event.application.port.in;

import org.radon.pushup.features.event.domain.DlqEvent;

public interface SendDlqEventUseCase {
    void sendEvent(DlqEvent dlqEvent);
}
