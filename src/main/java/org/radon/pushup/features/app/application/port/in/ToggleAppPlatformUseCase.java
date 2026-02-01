package org.radon.pushup.features.app.application.port.in;

import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.domain.Platform;

import java.util.UUID;

public interface ToggleAppPlatformUseCase {
    App toggleAppPlatform(UUID appId, Platform platform);
}
