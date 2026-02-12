package org.radon.pushup.features.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.app.presentation.dto.AppResponse;
import org.radon.pushup.features.user.domain.Role;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponse {
    private String username;
    private String email;
    private String phone;
    private String role;
//    private Set<AppResponse> apps;
    private Set<String> relatedApps;
    private Timestamp created_at;
    private Timestamp updated_at;
}
