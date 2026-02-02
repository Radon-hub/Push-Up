package org.radon.pushup.features.app.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.radon.pushup.features.app.domain.ApiKeyStatus;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ApiKeyResponse {
    private String api_key_prefix;
    private ApiKeyStatus api_key_status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
