package org.radon.pushup.features.app.presentation;

import org.radon.pushup.features.app.domain.ApiKey;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.presentation.dto.ApiKeyResponse;
import org.radon.pushup.features.app.presentation.dto.AppResponse;

import java.util.stream.Collectors;

public class AppDtoMappers {

    public static AppResponse toAppResponse(App app) {
        return new AppResponse(
                app.getId(),
                app.getApi_key().stream().map(item -> new ApiKeyResponse(item.getApi_key_prefix(),item.getApi_key_status(),item.getCreated_at(),item.getUpdated_at())).collect(Collectors.toSet()),
                app.getName(),
                app.getPlatform(),
                app.getStatus(),
                app.getCreated_at(),
                app.getUpdated_at()
        );
    }

    public static ApiKeyResponse toApiKeyResponse(ApiKey apiKey) {
        return new ApiKeyResponse(
                apiKey.getApi_key_prefix(),
                apiKey.getApi_key_status(),
                apiKey.getCreated_at(),
                apiKey.getUpdated_at()
        );
    }
}
