package org.radon.pushup.features.tenant.application.service;

import org.radon.pushup.features.tenant.application.port.in.CreateTenantUseCase;
import org.radon.pushup.features.tenant.application.port.in.GetUserTenantUseCase;
import org.radon.pushup.features.tenant.application.port.out.TenantRepository;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.presentation.dto.CreateTenantRequest;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantService implements CreateTenantUseCase, GetUserTenantUseCase {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant createTenant(CreateTenantRequest request) {
        return tenantRepository.createTenant(request);
    }

    @Transactional(readOnly = true)
    @Override
    public Tenant getTenant() {
        return tenantRepository.getTenant();
    }
}
