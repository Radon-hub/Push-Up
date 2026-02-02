package org.radon.pushup.features.app.presentation.controller;

import org.radon.pushup.features.app.application.port.in.*;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.app.domain.Platform;
import org.radon.pushup.features.app.infrastructure.mapper.AppMappers;
import org.radon.pushup.features.app.presentation.AppDtoMappers;
import org.radon.pushup.features.app.presentation.dto.*;
import org.radon.pushup.shared.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/apps")
public class AppRestController {

    private final CreateAppUseCase createAppUseCase;
    private final ChangeAppStatusUseCase changeAppStatusUseCase;
    private final ToggleAppPlatformUseCase toggleAppPlatformUseCase;
    private final AddUserToAppUseCase addUserToAppUseCase;

    public AppRestController(CreateAppUseCase createAppUseCase, ChangeAppStatusUseCase changeAppStatusUseCase, ToggleAppPlatformUseCase toggleAppPlatformUseCase, AddUserToAppUseCase addUserToAppUseCase) {
        this.createAppUseCase = createAppUseCase;
        this.changeAppStatusUseCase = changeAppStatusUseCase;
        this.toggleAppPlatformUseCase = toggleAppPlatformUseCase;
        this.addUserToAppUseCase = addUserToAppUseCase;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<Response<AppResponse>> createApp(@RequestBody CreateAppRequest request) {
        var result = createAppUseCase.createApp(request.name());
        return  ResponseEntity.ok().body(new Response<>(AppDtoMappers.toAppResponse(result),null));
    }

    @PatchMapping("status")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<Response<AppResponse>> updateStatusApp(@RequestBody UpdateAppStatusRequest request) {
        var result = changeAppStatusUseCase.updateAppStatus(request.status(),request.appId());
        return ResponseEntity.ok().body(new Response<>(AppDtoMappers.toAppResponse(result),null));
    }

    @PostMapping("platform")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<Response<AppResponse>> toggleAppPlatforms(@RequestBody ToggleAppPlatformRequest request) {
        var result = toggleAppPlatformUseCase.toggleAppPlatform(request.appId(),request.platform());
        return ResponseEntity.ok().body(new Response<>(AppDtoMappers.toAppResponse(result),null));
    }

    @PostMapping("user")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Response<AppResponse>> addUserToApp(@RequestBody AddUserToAppRequest request) {
        var result = addUserToAppUseCase.addUserToApp(request.appId(),request.userId());
        return ResponseEntity.ok().body(new Response<>(AppDtoMappers.toAppResponse(result),null));
    }

}
