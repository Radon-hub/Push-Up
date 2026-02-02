package org.radon.pushup.features.app.application.port.in;

import org.radon.pushup.features.app.domain.ApiKey;
import org.radon.pushup.features.app.presentation.dto.ApiKeyResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface ToggleApiKeyStatusEnableUseCase {
    ApiKey toggleApiKeyStatusEnable(Long apiKeyId);
}
