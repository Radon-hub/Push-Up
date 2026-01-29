package org.radon.pushup.features.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthTokensResponse {
    private String access_token;
    private String refresh_token;
}
