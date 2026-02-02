package org.radon.pushup.features.app.presentation.controller;

import org.radon.pushup.features.app.application.port.in.GenerateApiKeyUseCase;
import org.radon.pushup.features.app.application.port.in.RevokeApiKeyUseCase;
import org.radon.pushup.features.app.application.port.in.RotateApiKeyUseCase;
import org.radon.pushup.features.app.application.port.in.ToggleApiKeyStatusEnableUseCase;
import org.radon.pushup.features.app.presentation.AppDtoMappers;
import org.radon.pushup.features.app.presentation.dto.ApiKeyIDRequest;
import org.radon.pushup.features.app.presentation.dto.ApiKeyResponse;
import org.radon.pushup.features.app.presentation.dto.AppIDRequest;
import org.radon.pushup.shared.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/apps/api-key")
public class ApiKeyRestController {


    private final GenerateApiKeyUseCase generateApiKeyUseCase;
    private final ToggleApiKeyStatusEnableUseCase toggleApiKeyStatusEnableUseCase;
    private final RevokeApiKeyUseCase revokeApiKeyUseCase;
    private final RotateApiKeyUseCase rotateApiKeyUseCase;

    public ApiKeyRestController(GenerateApiKeyUseCase generateApiKeyUseCase, ToggleApiKeyStatusEnableUseCase toggleApiKeyStatusEnableUseCase, RevokeApiKeyUseCase revokeApiKeyUseCase, RotateApiKeyUseCase rotateApiKeyUseCase) {
        this.generateApiKeyUseCase = generateApiKeyUseCase;
        this.toggleApiKeyStatusEnableUseCase = toggleApiKeyStatusEnableUseCase;
        this.revokeApiKeyUseCase = revokeApiKeyUseCase;
        this.rotateApiKeyUseCase = rotateApiKeyUseCase;
    }


    @PostMapping("generate")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<Response<String>> generateApiKey(@RequestBody AppIDRequest request) {
        var result = generateApiKeyUseCase.generateApiKey(request.appId());
        return ResponseEntity.ok().body(new Response<>(result,null));
    }

    @PostMapping("status/toggle")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<Response<ApiKeyResponse>> toggleApiKeyStatusEnable(@RequestBody ApiKeyIDRequest request) {
        var result = toggleApiKeyStatusEnableUseCase.toggleApiKeyStatusEnable(request.apiKeyId());
        return ResponseEntity.ok().body(new Response<>(AppDtoMappers.toApiKeyResponse(result),null));
    }

    @PostMapping("revoke")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<Response<ApiKeyResponse>> revokeApiKey(@RequestBody ApiKeyIDRequest request) {
        var result = revokeApiKeyUseCase.revokeApiKey(request.apiKeyId());
        return ResponseEntity.ok().body(new Response<>(AppDtoMappers.toApiKeyResponse(result),null));
    }

    @PostMapping("rotate")
    @PreAuthorize("hasRole('OWNER') || hasRole('DEVELOPER')")
    public ResponseEntity<Response<String>> rotateApiKey(@RequestBody ApiKeyIDRequest request) {
        var result = rotateApiKeyUseCase.rotateApiKey(request.apiKeyId());
        return ResponseEntity.ok().body(new Response<>(result,null));
    }

}
