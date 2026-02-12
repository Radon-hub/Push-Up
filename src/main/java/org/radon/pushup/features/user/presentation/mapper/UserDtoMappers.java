package org.radon.pushup.features.user.presentation.mapper;

import org.radon.pushup.features.app.presentation.AppDtoMappers;
import org.radon.pushup.features.user.domain.Role;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.presentation.dto.UserCreateRequest;
import org.radon.pushup.features.user.presentation.dto.UserResponse;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMappers {

    public static UserResponse createUserResponseFromUser(User user) {
        return new UserResponse(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().getRole(),
                user.getApps().stream().map(it -> it.getId().toString()).collect(Collectors.toSet()),
                user.getCreated_at(),
                user.getUpdated_at()
        );
    }

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
