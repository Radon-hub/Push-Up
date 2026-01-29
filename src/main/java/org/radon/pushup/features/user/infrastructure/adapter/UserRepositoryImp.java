package org.radon.pushup.features.user.infrastructure.adapter;

import jdk.jfr.Registered;
import org.radon.pushup.features.user.application.port.out.UserRepository;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.infrastructure.repository.UserJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.radon.pushup.features.user.infrastructure.repository.mapper.UserMappers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImp(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User createUser(User user) {
        Optional<UserEntity> optionalUser = userJpaRepository.findUserEntityByEmailOrPhone(user.getEmail(), user.getPhone());
        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        userJpaRepository.save(UserMappers.fromUserToUserEntity(user));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity optionalUser = userJpaRepository.findUserEntityByUsername(username).orElseThrow(() -> new IllegalStateException("Username not found"));
        return UserMappers.fromUserEntityToUser(optionalUser);
    }
}
