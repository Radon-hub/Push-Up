package org.radon.pushup.features.app.application.service;

import org.radon.pushup.features.app.application.port.in.*;
import org.radon.pushup.features.app.application.port.out.AppRepository;
import org.radon.pushup.features.app.domain.ApiKey;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.app.domain.Platform;
import org.radon.pushup.features.app.presentation.dto.ApiKeyResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppService implements CreateAppUseCase, ChangeAppStatusUseCase, ToggleAppPlatformUseCase , AddUserToAppUseCase  {

    private final AppRepository appRepository;

    public AppService(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Override
    public App createApp(String name) {
        return appRepository.createApp(name);
    }

    @Override
    public App updateAppStatus(AppStatus appStatus, UUID appId) {
        return appRepository.updateAppStatus(appStatus, appId);
    }


    @Override
    public App toggleAppPlatform(UUID appId, Platform platform) {
        return appRepository.toggleAppPlatform(appId, platform);
    }

    @Override
    public App addUserToApp(UUID appId, UUID userId) {
        return appRepository.addUserToApp(appId, userId);
    }

}
