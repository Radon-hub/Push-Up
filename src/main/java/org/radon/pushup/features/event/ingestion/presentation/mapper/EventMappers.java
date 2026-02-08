package org.radon.pushup.features.event.ingestion.presentation.mapper;

import org.radon.pushup.features.app.domain.ApiKey;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.infrastructure.mapper.AppMappers;
import org.radon.pushup.features.app.infrastructure.repository.entities.ApiKeyEntity;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventMappers {

    public static EventModel fromEventRequestToEventModel(
            SendEventRequest request,
            UUID tenantId,
            UUID appId
    ){
        return new EventModel(
                request.eventId(),
                tenantId,
                appId,
                request.userId(),
                request.eventName(),
                request.location(),
                request.schemaVersion(),
                request.appVersion(),
                request.device(),
                request.platform(),
                System.currentTimeMillis(),
                0,
                request.properties()
        );
    }

    public static Tenant fromApiKeyDataToTenant(TenantEntity tenantEntity, AppEntity appEntity, ApiKeyEntity apiKeyEntity){
        return new Tenant(
                tenantEntity.getId(),
                tenantEntity.getName(),
                tenantEntity.getStatus(),
                Set.of(),
                Set.of(
                        new App(
                                appEntity.getId(),
                                Set.of(
                                        new ApiKey(
                                                apiKeyEntity.getId(),
                                                apiKeyEntity.getApi_key(),
                                                apiKeyEntity.getApi_key_prefix(),
                                                apiKeyEntity.getApi_key_status(),
                                                apiKeyEntity.getCreated_at(),
                                                apiKeyEntity.getUpdated_at()
                                        )
                                ),
                                appEntity.getName(),
                                appEntity.getPlatform().stream().map(AppMappers::toPlatformFromPlatformEntity).collect(Collectors.toSet()),
                                appEntity.getStatus(),
                                appEntity.getCreated_at(),
                                appEntity.getUpdated_at()
                        )
                ),
                tenantEntity.getCreated_at(),
                tenantEntity.getUpdated_at()
        );
    }
}
