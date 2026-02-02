package org.radon.pushup.features.app.application.port.in;

import org.radon.pushup.features.app.domain.ApiKey;
import org.springframework.web.bind.annotation.RequestBody;

public interface RevokeApiKeyUseCase {
    ApiKey revokeApiKey(Long apiKeyId);
}
