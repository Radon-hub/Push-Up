package org.radon.pushup.features.tenant.presentation.mapper;

import org.radon.pushup.features.app.presentation.AppDtoMappers;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;
import org.radon.pushup.features.user.presentation.mapper.UserDtoMappers;

import java.util.stream.Collectors;

public class TenantDtoMapper {
    public static TenantResponse toTenantResponse(Tenant tenant) {
        return new TenantResponse(
                tenant.getId(),
                tenant.getName(),
                tenant.getStatus(),
                tenant.getUsers().stream().map(UserDtoMappers::createUserResponseFromUser).collect(Collectors.toSet()),
                tenant.getApps().stream().map(AppDtoMappers::toAppResponse).collect(Collectors.toSet()),
                tenant.getCreated_at(),
                tenant.getUpdated_at()
        );
    }
}
