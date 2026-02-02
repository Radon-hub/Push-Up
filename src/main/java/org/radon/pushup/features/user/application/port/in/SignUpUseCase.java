package org.radon.pushup.features.user.application.port.in;

import org.radon.pushup.features.user.domain.User;

public interface SignUpUseCase {
    User signUp(User user);
}
