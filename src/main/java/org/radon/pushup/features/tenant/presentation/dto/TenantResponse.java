package org.radon.pushup.features.tenant.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.tenant.domain.TenantStatus;
import org.radon.pushup.features.user.domain.User;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class TenantResponse {
    private UUID id;
    private String name;
    private TenantStatus status;
    private Set<User> users;
    private Set<App> apps;
    private Timestamp created_at;
    private Timestamp updated_at;
}
