package org.radon.pushup.features.user.infrastructure.repository;

import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findUserEntityByEmailOrPhone(String email, String phone);

    Optional<UserEntity> findUserEntityByUsername(String username);

    Optional<UserEntity> findUserEntityByEmailOrPhoneOrUsername(String email, String phone, String username);
}
