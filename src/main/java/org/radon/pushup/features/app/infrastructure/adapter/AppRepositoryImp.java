package org.radon.pushup.features.app.infrastructure.adapter;

import jakarta.transaction.Transactional;
import org.radon.pushup.features.app.application.port.out.AppRepository;
import org.radon.pushup.features.app.domain.*;
import org.radon.pushup.features.app.infrastructure.mapper.AppMappers;
import org.radon.pushup.features.app.infrastructure.repository.ApiKeyJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.AppJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.PlatformJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.user.infrastructure.repository.UserJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.radon.pushup.shared.apiKeys.ApiKeyGenerator;
import org.radon.pushup.shared.apiKeys.ApiKeyHasher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public class AppRepositoryImp implements AppRepository {

    private final AppJpaRepository appJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ApiKeyJpaRepository apiKeyJpaRepository;
    private final PlatformJpaRepository platformJpaRepository;

    public AppRepositoryImp(AppJpaRepository appJpaRepository, UserJpaRepository userJpaRepository, ApiKeyJpaRepository apiKeyJpaRepository, PlatformJpaRepository platformJpaRepository) {
        this.appJpaRepository = appJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.apiKeyJpaRepository = apiKeyJpaRepository;
        this.platformJpaRepository = platformJpaRepository;
    }



    @Override
    public App createApp(String name) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalArgumentException("Unauthenticated");
        }

        String username = auth.getName();

        UserEntity userEntity = userJpaRepository
                .findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(userEntity.getTenant() == null) {
            throw new IllegalArgumentException("Tenant not found!");
        }

        Optional<AppEntity> appEntity = appJpaRepository.findByNameAndTenant(name,userEntity.getTenant());

        if(appEntity.isPresent()) {
            throw new IllegalArgumentException("Duplicate app name!");
        }

        var savedApp = appJpaRepository.save(new AppEntity(name, userEntity.getTenant()));

        return AppMappers.toAppFromAppEntity(savedApp);

    }

    @Transactional
    @Override
    public App updateAppStatus(AppStatus appStatus, UUID appId) {

        var app = appJpaRepository.findById(appId).orElseThrow(() -> new IllegalArgumentException("App not found"));

        app.setStatus(appStatus);

        return AppMappers.toAppFromAppEntity(app);

    }

    @Override
    public String generateApiKey(UUID appId) {
        var app = appJpaRepository.findById(appId).orElseThrow(() -> new IllegalArgumentException("App not found"));

        var apiKeyPair = ApiKeyGenerator.generate();

        var apiKeyPrefix = apiKeyPair.getKey();
        
        var apiKeyHash = apiKeyPair.getValue();

        var apiKey = apiKeyPrefix + apiKeyHash;

        try{
            apiKeyHash = ApiKeyHasher.hash(apiKey);
        }catch (Exception e){
            throw new IllegalArgumentException("Invalid API Key!");
        }

        apiKeyJpaRepository.save(AppMappers.toApiKeyEntityFromApiKey(new ApiKey(
                apiKeyHash,
                apiKeyPrefix,
                ApiKeyStatus.ACTIVE,
                app
        )));

        return apiKey;
    }

    @Override
    public App toggleAppPlatform(UUID appId, Platform platform) {

        var app = appJpaRepository.findById(appId).orElseThrow(() -> new IllegalArgumentException("App not found"));
        var platformEntity = platformJpaRepository.findByPlatform(platform).orElseThrow(() -> new IllegalArgumentException("Platform not found"));

        var appPlatforms = app.getPlatform();

        if(appPlatforms.contains(platformEntity)) {
            appPlatforms.remove(platformEntity);
        }else{
            appPlatforms.add(platformEntity);
        }

        appJpaRepository.save(app);

        return AppMappers.toAppFromAppEntity(app);

    }

    @Override
    public App addUserToApp(UUID appId, UUID userId) {

        var app = appJpaRepository.findById(appId).orElseThrow(() -> new IllegalArgumentException("App not found"));

        var user = userJpaRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(app.getUsers().contains(user)) {
            throw new IllegalArgumentException("Duplicate user found!");
        }

        user.getApps().add(app);

        userJpaRepository.save(user);

        return AppMappers.toAppFromAppEntity(app);
    }


}
