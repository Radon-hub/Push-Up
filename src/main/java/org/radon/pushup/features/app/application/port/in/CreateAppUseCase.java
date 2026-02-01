package org.radon.pushup.features.app.application.port.in;

import org.radon.pushup.features.app.domain.App;

public interface CreateAppUseCase {
    App createApp(String name);
}
