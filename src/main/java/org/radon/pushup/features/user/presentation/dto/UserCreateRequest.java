package org.radon.pushup.features.user.presentation.dto;

import lombok.Data;
import org.radon.pushup.features.user.domain.Role;
import org.radon.pushup.features.user.infrastructure.repository.entities.RoleEntity;

@Data
public class UserCreateRequest {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;


}
