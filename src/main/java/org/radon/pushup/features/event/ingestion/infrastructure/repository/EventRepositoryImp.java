package org.radon.pushup.features.event.ingestion.infrastructure.repository;

import org.radon.pushup.features.app.domain.ApiKeyStatus;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.app.infrastructure.repository.ApiKeyJpaRepository;
import org.radon.pushup.features.event.ingestion.application.port.out.EventRepository;
import org.radon.pushup.features.event.ingestion.domain.EventModel;
import org.radon.pushup.features.event.ingestion.presentation.dto.SendEventRequest;
import org.radon.pushup.features.tenant.domain.TenantStatus;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyException;
import org.radon.pushup.shared.aop.exceptionHandling.model.ApiKeyNotFoundException;
import org.radon.pushup.shared.aop.exceptionHandling.model.InvalidEventException;
import org.radon.pushup.shared.apiKeys.ApiKeyHasher;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.stream.Collectors;

@Repository
public class EventRepositoryImp implements EventRepository {

    private final ApiKeyJpaRepository apiKeyJpaRepository;

    public EventRepositoryImp(ApiKeyJpaRepository apiKeyJpaRepository) {
        this.apiKeyJpaRepository = apiKeyJpaRepository;
    }


    @Override
    public EventModel fetchApiKeyData(String apiKey, SendEventRequest request) throws NoSuchAlgorithmException {


        var hash = ApiKeyHasher.hash(apiKey);

        var apiKeyEntity = apiKeyJpaRepository.findByApiKey(hash);

        if (apiKeyEntity == null) {
            throw new ApiKeyNotFoundException();
        }

        if(apiKeyEntity.getApi_key_status() == ApiKeyStatus.DISABLED) {
            throw new ApiKeyException("API KEY status is not ACTIVE!");
        }else if(apiKeyEntity.getApi_key_status() == ApiKeyStatus.REVOKED) {
            throw new ApiKeyException("This API KEY is no longer valid!");
        }else if(apiKeyEntity.getApi_key_status() == ApiKeyStatus.ROTATED) {
            throw new ApiKeyException("API KEY has been changed!");
        }

        if(apiKeyEntity.getApp().getStatus() != AppStatus.ACTIVE) {
            throw new InvalidEventException("App status is not ACTIVE!",apiKeyEntity.getApp().getTenant().getId().toString(),apiKeyEntity.getApp().getId().toString());
        }

        if(request.platform() == null){
            throw new InvalidEventException("You have to specify platform!",apiKeyEntity.getApp().getTenant().getId().toString(),apiKeyEntity.getApp().getId().toString());
        }

        var filteredPlatform = apiKeyEntity.getApp().getPlatform().stream().filter(item -> item.getPlatform() == request.platform()).collect(Collectors.toSet());

        if(filteredPlatform.isEmpty()){
            throw new InvalidEventException("Can not send event on this platform!",apiKeyEntity.getApp().getTenant().getId().toString(),apiKeyEntity.getApp().getId().toString());
        }

        if(apiKeyEntity.getApp().getTenant().getStatus() != TenantStatus.ACTIVE) {
            throw new InvalidEventException("Tenant not able to send events!",apiKeyEntity.getApp().getTenant().getId().toString(),apiKeyEntity.getApp().getId().toString());
        }


        return new EventModel(
                request.eventId(),
                apiKeyEntity.getApp().getTenant().getId(),
                apiKeyEntity.getApp().getId(),
                request.userId(),
                request.eventName(),
                request.location(),
                request.platform(),
                System.currentTimeMillis(),
                0,
                request.properties()
        );


    }
}
