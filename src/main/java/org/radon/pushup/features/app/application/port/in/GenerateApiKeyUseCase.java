package org.radon.pushup.features.app.application.port.in;

import java.util.UUID;

public interface GenerateApiKeyUseCase {
    String generateApiKey(UUID appId);
}
