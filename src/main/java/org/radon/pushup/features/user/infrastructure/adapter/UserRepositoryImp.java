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
import org.radon.pushup.shared.aop.exceptionHandling.model.*;
import org.springframework.security.core.Authentication;
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

        var ownerUser = getUserWithTenantExistence();

        Optional<UserEntity> optionalUser = userJpaRepository.findUserEntityByEmailOrPhoneOrUsername(user.getEmail(), user.getPhone(),user.getUsername());

        TenantEntity ownerTenant = ownerUser.getTenant();

        RoleEntity roleEntity = roleJpaRepository.findByName(user.getRole().getRole()).orElseThrow(RoleNotFoundException::new);

        if (optionalUser.isPresent()) {
            throw new UserExistException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userJpaRepository.save(UserMappers.fromUserToUserEntity(user,ownerTenant,roleEntity));

        return user;
    }

    @Override
    public User signUp(User user) {

        var existedUser = userJpaRepository.findUserEntityByEmailOrPhone(user.getEmail(), user.getPhone());

        if(existedUser.isPresent()){
            throw new UserExistException();
        }

        RoleEntity roleEntity = roleJpaRepository.findByName("OWNER").orElseThrow(RoleNotFoundException::new);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userJpaRepository.save(UserMappers.fromUserToUserEntity(user,roleEntity));

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity optionalUser = userJpaRepository.findUserEntityByUsername(username).orElseThrow(UserNotFoundException::new);
        return UserMappers.fromUserEntityToUser(optionalUser);
    }

    private UserEntity getUserWithTenantExistence(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AccessDeniedException("Not authenticated!");
        }

        String username = auth.getName();

        UserEntity userEntity = userJpaRepository
                .findUserEntityByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if(userEntity.getTenant() == null) {
            throw new TenantNotFoundException();
        }

        return userEntity;
    }
}
