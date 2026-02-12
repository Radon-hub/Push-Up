package org.radon.pushup.features.app.application.service;

import org.radon.pushup.features.app.application.port.in.GenerateApiKeyUseCase;
import org.radon.pushup.features.app.application.port.in.RevokeApiKeyUseCase;
import org.radon.pushup.features.app.application.port.in.RotateApiKeyUseCase;
import org.radon.pushup.features.app.application.port.in.ToggleApiKeyStatusEnableUseCase;
import org.radon.pushup.features.app.application.port.out.ApiKeyRepository;
import org.radon.pushup.features.app.domain.ApiKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ApiKeyService implements GenerateApiKeyUseCase, ToggleApiKeyStatusEnableUseCase, RevokeApiKeyUseCase, RotateApiKeyUseCase {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Transactional
    @Override
    public ApiKey toggleApiKeyStatusEnable(Long apiKeyId) {
        return apiKeyRepository.toggleApiKeyStatusEnable(apiKeyId);
    }

    @Transactional
    @Override
    public ApiKey revokeApiKey(Long apiKeyId) {
        return apiKeyRepository.revokeApiKey(apiKeyId);
    }

    @Transactional
    @Override
    public String rotateApiKey(Long apiKeyId) {
        return apiKeyRepository.rotateApiKey(apiKeyId);
    }

    @Transactional
    @Override
    public String generateApiKey(UUID appId) {
        return apiKeyRepository.generateApiKey(appId);
    }

}
