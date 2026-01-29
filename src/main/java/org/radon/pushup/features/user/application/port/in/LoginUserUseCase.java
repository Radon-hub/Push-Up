package org.radon.pushup.features.user.application.port.in;

import org.radon.pushup.features.user.presentation.dto.UserAuthTokensResponse;
import org.radon.pushup.features.user.presentation.dto.UserLoginRequest;

public interface LoginUserUseCase {
    UserAuthTokensResponse login(UserLoginRequest userLoginRequest);
}
