package org.radon.pushup.features.tenant.application.port.out;

import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.presentation.dto.CreateTenantRequest;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;

public interface TenantRepository {
    Tenant createTenant(CreateTenantRequest request);
}
