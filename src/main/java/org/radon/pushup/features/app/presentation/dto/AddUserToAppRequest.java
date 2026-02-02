package org.radon.pushup.features.app.presentation.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public record AddUserToAppRequest(
        UUID appId,
        UUID userId
) {
}
