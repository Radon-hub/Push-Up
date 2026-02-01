package org.radon.pushup.features.app.presentation.controller;

import org.radon.pushup.features.app.application.port.in.*;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.app.domain.Platform;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/apps")
public class AppRestController {

    private final CreateAppUseCase createAppUseCase;
    private final ChangeAppStatusUseCase changeAppStatusUseCase;
    private final GenerateApiKeyUseCase generateApiKeyUseCase;
    private final ToggleAppPlatformUseCase toggleAppPlatformUseCase;
    private final AddUserToAppUseCase addUserToAppUseCase;

    public AppRestController(CreateAppUseCase createAppUseCase, ChangeAppStatusUseCase changeAppStatusUseCase, GenerateApiKeyUseCase generateApiKeyUseCase, ToggleAppPlatformUseCase toggleAppPlatformUseCase, AddUserToAppUseCase addUserToAppUseCase) {
        this.createAppUseCase = createAppUseCase;
        this.changeAppStatusUseCase = changeAppStatusUseCase;
        this.generateApiKeyUseCase = generateApiKeyUseCase;
        this.toggleAppPlatformUseCase = toggleAppPlatformUseCase;
        this.addUserToAppUseCase = addUserToAppUseCase;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<App> createApp(@RequestParam String name) {
        var result = createAppUseCase.createApp(name);
        return  ResponseEntity.ok().body(result);
    }

    @PatchMapping("status")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<App> updateStatusApp(@RequestParam UUID appId, @RequestParam AppStatus status) {
        var result = changeAppStatusUseCase.updateAppStatus(status,appId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("generate-api-key")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<String> generateApiKey(@RequestParam UUID appId) {
        var result = generateApiKeyUseCase.generateApiKey(appId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("platform")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<App> toggleAppPlatforms(@RequestParam UUID appId,@RequestParam Platform platform) {
        var result = toggleAppPlatformUseCase.toggleAppPlatform(appId,platform);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("user")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<App> addUserToApp(@RequestParam UUID appId,@RequestParam UUID userId) {
        var result = addUserToAppUseCase.addUserToApp(appId,userId);
        return ResponseEntity.ok().body(result);
    }

}
