package org.radon.pushup.features.user.presentation.dto;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;
}
