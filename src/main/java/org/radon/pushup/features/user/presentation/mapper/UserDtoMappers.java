package org.radon.pushup.features.user.presentation.mapper;

import org.radon.pushup.features.user.domain.Role;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.presentation.dto.UserCreateRequest;

import java.util.Set;

public class UserDtoMappers {

    public static User createUserFromDto(UserCreateRequest userCreateRequest) {
        return new User(
                userCreateRequest.getUsername(),
                userCreateRequest.getPassword(),
                userCreateRequest.getEmail(),
                userCreateRequest.getPhone(),
                new Role(userCreateRequest.getRole(), Set.of())
        );
    }
}
