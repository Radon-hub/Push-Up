package org.radon.pushup.features.app.application.port.in;

import org.radon.pushup.features.app.domain.ApiKey;

public interface RotateApiKeyUseCase {
    String rotateApiKey(Long apiKeyId);
}
