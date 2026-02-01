package org.radon.pushup.features.app.infrastructure.mapper;

import org.radon.pushup.features.app.domain.ApiKey;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.domain.Platform;
import org.radon.pushup.features.app.infrastructure.repository.entities.ApiKeyEntity;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.app.infrastructure.repository.entities.PlatformEntity;
import org.radon.pushup.features.tenant.infrastructure.mapper.TenantMappers;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class AppMappers {

    public static AppEntity toAppEntityFromApp(String name, TenantEntity tenantEntity) {
        return new AppEntity(
                name,
                tenantEntity
        );
    }

    public static App toAppFromAppEntity(AppEntity appEntity) {
        return new App(
                appEntity.getId(),
                TenantMappers.toTenantFromTenantEntity(appEntity.getTenant()),
                appEntity.getApi_key().stream().map(AppMappers::toApiKeyFromApiKeyEntity).collect(Collectors.toSet()),
                appEntity.getName(),
                appEntity.getPlatform().stream().map(AppMappers::toPlatformFromPlatformEntity).collect(Collectors.toSet()),
                appEntity.getStatus(),
                appEntity.getCreated_at(),
                appEntity.getUpdated_at()
        );
    }

    public static PlatformEntity toPlatformEntityFromPlatform(Platform platform) {
        return new PlatformEntity(
                platform
        );
    }

    public static Platform toPlatformFromPlatformEntity(PlatformEntity platformEntity) {
        return platformEntity.getPlatform();
    }

    public static ApiKeyEntity toApiKeyEntityFromApiKey(ApiKey apiKey) {
        return new ApiKeyEntity(
                apiKey.getApi_key(),
                apiKey.getApi_key_prefix(),
                apiKey.getApi_key_status(),
                apiKey.getApp()
        );
    }

    public static ApiKey toApiKeyFromApiKeyEntity(ApiKeyEntity apiKeyEntity) {
        return new ApiKey(
                apiKeyEntity.getId(),
                apiKeyEntity.getApi_key(),
                apiKeyEntity.getApi_key_prefix(),
                apiKeyEntity.getApi_key_status(),
                apiKeyEntity.getApp(),
                apiKeyEntity.getCreated_at(),
                apiKeyEntity.getUpdated_at()
        );
    }


}
