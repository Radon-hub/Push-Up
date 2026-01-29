package org.radon.pushup.features.user.application.port.in;

import org.radon.pushup.features.user.presentation.dto.UserAuthTokensResponse;
import org.springframework.web.bind.annotation.RequestParam;

public interface RefreshTokenUseCase {
    UserAuthTokensResponse refreshToken(String refreshToken);
}
