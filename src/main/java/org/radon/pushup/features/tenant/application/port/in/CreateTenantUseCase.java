package org.radon.pushup.features.tenant.application.port.in;

import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.presentation.dto.CreateTenantRequest;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;

public interface CreateTenantUseCase {
    Tenant createTenant(CreateTenantRequest request);
}
