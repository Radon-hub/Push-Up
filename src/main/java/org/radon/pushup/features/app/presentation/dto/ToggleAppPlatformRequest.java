package org.radon.pushup.features.app.presentation.dto;

import org.radon.pushup.features.app.domain.Platform;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public record ToggleAppPlatformRequest(
        UUID appId,
        Platform platform
) {
}
