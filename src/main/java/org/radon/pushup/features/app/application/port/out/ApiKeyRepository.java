package org.radon.pushup.features.app.application.port.out;

import org.radon.pushup.features.app.domain.ApiKey;

import java.util.UUID;

public interface ApiKeyRepository {
    String generateApiKey(UUID appId);
    ApiKey toggleApiKeyStatusEnable(Long apiKeyId);
    ApiKey revokeApiKey(Long apiKeyId);
    String rotateApiKey(Long apiKeyId);
}
