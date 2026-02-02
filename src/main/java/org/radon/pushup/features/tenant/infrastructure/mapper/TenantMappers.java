package org.radon.pushup.features.tenant.infrastructure.mapper;

import org.radon.pushup.features.app.infrastructure.mapper.AppMappers;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.radon.pushup.features.user.infrastructure.repository.mapper.UserMappers;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class TenantMappers {

    public static TenantEntity toTenantEntityFromTenant(Tenant tenant) {
        return new TenantEntity(
                tenant.getId(),
                tenant.getName(),
                tenant.getStatus(),
                Set.of(),
                Set.of(),
                tenant.getCreated_at(),
                tenant.getUpdated_at()
        );
    }

    public static Tenant toTenantFromTenantEntity(TenantEntity tenant) {
        return new Tenant(
                tenant.getId(),
                tenant.getName(),
                tenant.getStatus(),
                tenant.getUsers().stream().map(UserMappers::fromUserEntityToUser).collect(Collectors.toSet()),
                tenant.getApps().stream().map(AppMappers::toAppFromAppEntity).collect(Collectors.toSet()),
                tenant.getCreated_at(),
                tenant.getUpdated_at()
        );
    }

}
