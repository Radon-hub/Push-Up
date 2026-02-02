package org.radon.pushup.features.tenant.presentation.controller;

import org.radon.pushup.features.tenant.application.port.in.CreateTenantUseCase;
import org.radon.pushup.features.tenant.application.port.in.GetUserTenantUseCase;
import org.radon.pushup.features.tenant.infrastructure.mapper.TenantMappers;
import org.radon.pushup.features.tenant.presentation.dto.CreateTenantRequest;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;
import org.radon.pushup.features.tenant.presentation.mapper.TenantDtoMapper;
import org.radon.pushup.shared.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantRestController {

    private final CreateTenantUseCase createTenantUseCase;
    private final GetUserTenantUseCase getUserTenantUseCase;

    public TenantRestController(CreateTenantUseCase createTenantUseCase, GetUserTenantUseCase getUserTenantUseCase) {
        this.createTenantUseCase = createTenantUseCase;
        this.getUserTenantUseCase = getUserTenantUseCase;
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Response<TenantResponse>> createTenant(@RequestBody CreateTenantRequest request) {
        var tenant = createTenantUseCase.createTenant(request);
        return ResponseEntity.ok(new Response<>(TenantDtoMapper.toTenantResponse(tenant),null));
    }

    @GetMapping("")
    public ResponseEntity<Response<TenantResponse>> getTenant() {
        var tenant = getUserTenantUseCase.getTenant();
        return ResponseEntity.ok(new Response<>(TenantDtoMapper.toTenantResponse(tenant),null));
    }

}
