package org.radon.pushup.features.user.infrastructure.adapter;

import jdk.jfr.Registered;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantJpaRepository;
import org.radon.pushup.features.user.application.port.out.UserRepository;
import org.radon.pushup.features.user.domain.User;
import org.radon.pushup.features.user.infrastructure.repository.RoleJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.UserJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.entities.RoleEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.radon.pushup.features.user.infrastructure.repository.mapper.UserMappers;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final TenantJpaRepository tenantJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryImp(UserJpaRepository userJpaRepository, TenantJpaRepository tenantJpaRepository, RoleJpaRepository roleJpaRepository, PasswordEncoder passwordEncoder) {
        this.userJpaRepository = userJpaRepository;
        this.tenantJpaRepository = tenantJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) {

        Optional<UserEntity> optionalUser = userJpaRepository.findUserEntityByEmailOrPhone(user.getEmail(), user.getPhone());

        Optional<TenantEntity> ownerTenant = tenantJpaRepository.findByOwnerUsersName(SecurityContextHolder.getContext().getAuthentication().getName());

        Optional<RoleEntity> roleEntity = roleJpaRepository.findByName(user.getRole().getRole());

        if (optionalUser.isPresent()) {
            throw new IllegalStateException("User already exists");
        }

        if (ownerTenant.isEmpty()) {
            throw new IllegalStateException("No tenant exists");
        }

        if (roleEntity.isEmpty()) {
            throw new IllegalStateException("Role not found!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userJpaRepository.save(UserMappers.fromUserToUserEntity(user,UserMappers.fromTenantEntityToTenant(ownerTenant.get()),roleEntity.get()));

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity optionalUser = userJpaRepository.findUserEntityByUsername(username).orElseThrow(() -> new IllegalStateException("Username not found"));
        return UserMappers.fromUserEntityToUser(optionalUser);
    }
}
