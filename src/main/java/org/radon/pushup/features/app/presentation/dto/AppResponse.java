package org.radon.pushup.features.app.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.app.domain.Platform;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AppResponse {
    private UUID id;
    private Set<ApiKeyResponse> api_key;
    private String name;
    private Set<Platform> platform;
    private AppStatus status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
