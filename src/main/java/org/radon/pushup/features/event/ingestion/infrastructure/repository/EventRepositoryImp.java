package org.radon.pushup.features.event.ingestion.infrastructure.repository;

import org.radon.pushup.features.app.domain.*;
import org.radon.pushup.features.app.infrastructure.mapper.AppMappers;
import org.radon.pushup.features.app.infrastructure.repository.ApiKeyJpaRepository;
import org.radon.pushup.features.event.ingestion.application.port.out.EventRepository;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;
import org.radon.pushup.features.event.ingestion.presentation.mapper.EventMappers;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.domain.TenantStatus;
import org.radon.pushup.features.tenant.infrastructure.mapper.TenantMappers;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyException;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyNotFoundException;
import org.radon.pushup.shared.aop.exceptionHandling.model.InvalidArgsException;
import org.radon.pushup.shared.aop.exceptionHandling.model.InvalidEventException;
import org.radon.pushup.shared.apiKeys.ApiKeyHasher;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryImp implements EventRepository {

    private final ApiKeyJpaRepository apiKeyJpaRepository;

    public EventRepositoryImp(ApiKeyJpaRepository apiKeyJpaRepository) {
        this.apiKeyJpaRepository = apiKeyJpaRepository;
    }


    @Transactional(readOnly = true)
    @Override
    public Tenant fetchApiKeyData(String apiKeyHash) {

        var apiKeyEntity = apiKeyJpaRepository.findByApiKey(apiKeyHash);

        if (apiKeyEntity == null) {
            throw new ApiKeyNotFoundException();
        }

        var tenantEntity = apiKeyEntity.getApp().getTenant();
        var appEntity = apiKeyEntity.getApp();

        return EventMappers.fromApiKeyDataToTenant(tenantEntity,appEntity,apiKeyEntity);

    }
}
