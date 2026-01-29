package org.radon.pushup.features.tenant.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateTenantRequest {
    private String name;
}
