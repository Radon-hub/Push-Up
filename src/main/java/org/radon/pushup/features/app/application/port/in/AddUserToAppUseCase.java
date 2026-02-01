package org.radon.pushup.features.app.application.port.in;

import org.radon.pushup.features.app.domain.App;

import java.util.UUID;

public interface AddUserToAppUseCase {
    App addUserToApp(UUID appId, UUID userId);
}
