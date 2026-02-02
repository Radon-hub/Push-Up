package org.radon.pushup.features.app.presentation.dto;

import org.radon.pushup.features.app.domain.AppStatus;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public record UpdateAppStatusRequest(
        UUID appId,
        AppStatus status
) {
}
