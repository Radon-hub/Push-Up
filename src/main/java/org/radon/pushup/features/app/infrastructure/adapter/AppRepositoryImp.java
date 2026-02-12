package org.radon.pushup.features.app.infrastructure.adapter;

import jakarta.transaction.Transactional;
import org.radon.pushup.features.app.application.port.out.AppRepository;
import org.radon.pushup.features.app.domain.*;
import org.radon.pushup.features.app.infrastructure.mapper.AppMappers;
import org.radon.pushup.features.app.infrastructure.repository.ApiKeyJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.AppJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.PlatformJpaRepository;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.app.presentation.dto.ApiKeyResponse;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.radon.pushup.features.user.infrastructure.repository.UserJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.radon.pushup.shared.aop.exceptionHandling.model.*;
import org.radon.pushup.shared.apiKeys.ApiKeyGenerator;
import org.radon.pushup.shared.apiKeys.ApiKeyHasher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AppRepositoryImp implements AppRepository {

    private final AppJpaRepository appJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PlatformJpaRepository platformJpaRepository;

    public AppRepositoryImp(AppJpaRepository appJpaRepository, UserJpaRepository userJpaRepository, PlatformJpaRepository platformJpaRepository) {
        this.appJpaRepository = appJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.platformJpaRepository = platformJpaRepository;
    }


    @Override
    public App createApp(String name) {


        var userEntity = getUserWithTenantExistence();

        Optional<AppEntity> appEntity = appJpaRepository.findByNameAndTenant(name,userEntity.getTenant());

        if(appEntity.isPresent()) {
            throw new AppExistException();
        }

        var savedApp = appJpaRepository.save(AppMappers.toAppEntityFromApp(name,userEntity));

        userEntity.getApps().add(savedApp);

        userJpaRepository.save(userEntity);

        return AppMappers.toAppFromAppEntity(savedApp);

    }

    @Override
    public App updateAppStatus(AppStatus appStatus, UUID appId) {

        var userEntity = getUserWithTenantExistence();

        var app = appJpaRepository.findById(appId).orElseThrow(AppNotFoundException::new);

        if (!userEntity.getTenant().equals(app.getTenant())) {
            throw new TenantAccessException();
        }

        app.setStatus(appStatus);
        app.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        return AppMappers.toAppFromAppEntity(app);

    }


    @Override
    public App toggleAppPlatform(UUID appId, Platform platform) {

        var userEntity = getUserWithTenantExistence();

        var app = appJpaRepository.findById(appId).orElseThrow(AppNotFoundException::new);

        if (!userEntity.getTenant().equals(app.getTenant())) {
            throw new TenantAccessException();
        }

        var platformEntity = platformJpaRepository.findByPlatform(platform).orElseThrow(PlatformNotFoundException::new);

        var appPlatforms = app.getPlatform();

        if(appPlatforms.contains(platformEntity)) {
            appPlatforms.remove(platformEntity);
        }else{
            appPlatforms.add(platformEntity);
        }

        app.setUpdated_at(new Timestamp(System.currentTimeMillis()));

        appJpaRepository.save(app);

        return AppMappers.toAppFromAppEntity(app);

    }

    @Override
    public App addUserToApp(UUID appId, UUID userId) {

        var userEntity = getUserWithTenantExistence();

        var app = appJpaRepository.findById(appId).orElseThrow(AppNotFoundException::new);

        if (!userEntity.getTenant().equals(app.getTenant())) {
            throw new TenantAccessException();
        }

        var addingUser = userJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        if(app.getUsers().contains(addingUser)) {
            throw new UserExistException();
        }

        addingUser.getApps().add(app);

        userJpaRepository.save(addingUser);

        return AppMappers.toAppFromAppEntity(app);
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
