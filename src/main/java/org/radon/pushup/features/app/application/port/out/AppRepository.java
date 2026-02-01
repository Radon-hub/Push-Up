package org.radon.pushup.features.app.application.port.out;

import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.app.domain.Platform;

import java.util.UUID;

public interface AppRepository {
    App createApp(String name);
    App updateAppStatus(AppStatus appStatus, UUID appId);
    String generateApiKey(UUID appId);
    App toggleAppPlatform(UUID appId, Platform platform);
    App addUserToApp(UUID appId, UUID userId);
}
