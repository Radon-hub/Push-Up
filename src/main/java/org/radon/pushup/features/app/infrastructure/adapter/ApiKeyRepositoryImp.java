package org.radon.pushup.features.app.infrastructure.adapter;

import jakarta.transaction.Transactional;
import org.radon.pushup.features.app.application.port.out.ApiKeyRepository;
import org.radon.pushup.features.app.domain.ApiKey;
import org.radon.pushup.features.app.domain.ApiKeyStatus;
import org.radon.pushup.features.app.infrastructure.mapper.AppMappers;
import org.radon.pushup.features.app.infrastructure.repository.ApiKeyJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.AppJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.PlatformJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.UserJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.radon.pushup.shared.aop.exceptionHandling.model.*;
import org.radon.pushup.shared.apiKeys.ApiKeyGenerator;
import org.radon.pushup.shared.apiKeys.ApiKeyHasher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ApiKeyRepositoryImp implements ApiKeyRepository {

    private final ApiKeyJpaRepository apiKeyJpaRepository;
    private final UserJpaRepository userJpaRepository;

    private final AppJpaRepository appJpaRepository;
    private final PlatformJpaRepository platformJpaRepository;


    public ApiKeyRepositoryImp(ApiKeyJpaRepository apiKeyJpaRepository, UserJpaRepository userJpaRepository, AppJpaRepository appJpaRepository, PlatformJpaRepository platformJpaRepository) {
        this.apiKeyJpaRepository = apiKeyJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.appJpaRepository = appJpaRepository;
        this.platformJpaRepository = platformJpaRepository;
    }


    @Override
    public ApiKey toggleApiKeyStatusEnable(Long apiKeyId) {

        var userEntity = getUserWithTenantExistence();

        var apiKey = apiKeyJpaRepository.findById(apiKeyId).orElseThrow(AppNotFoundException::new);

        if (!userEntity.getTenant().equals(apiKey.getApp().getTenant())) {
            throw new TenantAccessException();
        }

        if(apiKey.getApi_key_status() == ApiKeyStatus.ACTIVE) {
            apiKey.setApi_key_status(ApiKeyStatus.DISABLED);
        }else if(apiKey.getApi_key_status() == ApiKeyStatus.DISABLED){
            apiKey.setApi_key_status(ApiKeyStatus.ACTIVE);
        }else{
            throw new ApiKeyException("Can not toggle API key status when API key Revoked or Rotated!");
        }

        apiKey.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        apiKeyJpaRepository.save(apiKey);

        return AppMappers.toApiKeyFromApiKeyEntity(apiKey);
    }

    @Override
    public ApiKey revokeApiKey(Long apiKeyId) {

        var userEntity = getUserWithTenantExistence();

        var apiKey = apiKeyJpaRepository.findById(apiKeyId).orElseThrow(ApiKeyNotFoundException::new);

        if (!userEntity.getTenant().equals(apiKey.getApp().getTenant())) {
            throw new TenantAccessException();
        }

        if(apiKey.getApi_key_status() != ApiKeyStatus.ACTIVE) {
            throw new ApiKeyException("Only Active API keys can be revoked!");
        }

        apiKey.setApi_key_status(ApiKeyStatus.REVOKED);

        apiKey.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        apiKeyJpaRepository.save(apiKey);

        return AppMappers.toApiKeyFromApiKeyEntity(apiKey);
    }

    @Transactional
    @Override
    public String rotateApiKey(Long apiKeyId) {
        var userEntity = getUserWithTenantExistence();

        var apiKeyEntity = apiKeyJpaRepository.findById(apiKeyId).orElseThrow(ApiKeyNotFoundException::new);

        if (!userEntity.getTenant().equals(apiKeyEntity.getApp().getTenant())) {
            throw new TenantAccessException();
        }

        if(apiKeyEntity.getApi_key_status() != ApiKeyStatus.ACTIVE) {
            throw new ApiKeyException("Only Active API keys can be rotated!");
        }

        apiKeyEntity.setApi_key_status(ApiKeyStatus.ROTATED);

        apiKeyEntity.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        apiKeyJpaRepository.save(apiKeyEntity);

        var app = appJpaRepository.findById(apiKeyEntity.getApp().getId()).orElseThrow(AppNotFoundException::new);

        var apiKeyPair = ApiKeyGenerator.generate();

        var apiKeyPrefix = apiKeyPair.getKey();

        var apiKeyHash = apiKeyPair.getValue();

        var apiKey = apiKeyPrefix + apiKeyHash;

        try{
            apiKeyHash = ApiKeyHasher.hash(apiKey);
        }catch (Exception e){
            throw new InvalidApiKeyException("Invalid API Key!");
        }

        apiKeyJpaRepository.save(AppMappers.toApiKeyEntityFromApiKey(new ApiKey(
                apiKeyHash,
                apiKeyPrefix,
                ApiKeyStatus.ACTIVE
        ),app));

        return apiKey;
    }

    @Override
    public String generateApiKey(UUID appId) {

        var userEntity = getUserWithTenantExistence();

        var app = appJpaRepository.findById(appId).orElseThrow(AppNotFoundException::new);

        if (!userEntity.getTenant().equals(app.getTenant())) {
            throw new TenantAccessException();
        }

        var keys = app.getApi_key().stream().filter(item -> item.getApi_key_status() == ApiKeyStatus.ACTIVE).collect(Collectors.toSet());

        if(!keys.isEmpty()) {
            throw new ApiKeyExistException();
        }

        var apiKeyPair = ApiKeyGenerator.generate();

        var apiKeyPrefix = apiKeyPair.getKey();

        var apiKeyHash = apiKeyPair.getValue();

        var apiKey = apiKeyPrefix + apiKeyHash;

        try{
            apiKeyHash = ApiKeyHasher.hash(apiKey);
        }catch (Exception e){
            throw new InvalidApiKeyException("Invalid API Key!");
        }

        apiKeyJpaRepository.save(AppMappers.toApiKeyEntityFromApiKey(new ApiKey(
                apiKeyHash,
                apiKeyPrefix,
                ApiKeyStatus.ACTIVE
        ),app));

        return apiKey;
    }

    private UserEntity getUserWithTenantExistence(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Not authenticated!");
        }

        String username = auth.getName();

        UserEntity userEntity = userJpaRepository
                .findUserEntityByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if(userEntity.getTenant() == null) {
            throw new TenantNotFoundException();
        }

        return userEntity;
    }


}
