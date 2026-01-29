package org.radon.pushup.features.tenant.presentation.mapper;

import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;

public class TenantDtoMapper {
    public static TenantResponse toTenantResponse(Tenant tenant) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getStatus(),
                tenant.getUsers(),
                tenant.getApps(),
                tenant.getCreated_at(),
                tenant.getUpdated_at()
        );
    }
}
