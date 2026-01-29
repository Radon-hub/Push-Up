package org.radon.pushup.features.tenant.presentation.controller;

import org.radon.pushup.features.tenant.application.port.in.CreateTenantUseCase;
import org.radon.pushup.features.tenant.infrastructure.mapper.TenantMappers;
import org.radon.pushup.features.tenant.presentation.dto.CreateTenantRequest;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;
import org.radon.pushup.features.tenant.presentation.mapper.TenantDtoMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantRestController {

    private final CreateTenantUseCase createTenantUseCase;

    public TenantRestController(CreateTenantUseCase createTenantUseCase) {
        this.createTenantUseCase = createTenantUseCase;
    }

    @PostMapping("create")
    @PreAuthorize("hasRole('OWNER')")
    public TenantResponse createTenant(@RequestBody CreateTenantRequest request) {
        var tenant = createTenantUseCase.createTenant(request);
        return TenantDtoMapper.toTenantResponse(tenant);
    }



}
