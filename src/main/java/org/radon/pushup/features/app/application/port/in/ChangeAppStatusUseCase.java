package org.radon.pushup.features.app.application.port.in;

import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.domain.AppStatus;

import java.util.UUID;

public interface ChangeAppStatusUseCase {
    App updateAppStatus(AppStatus appStatus, UUID appId);
}
