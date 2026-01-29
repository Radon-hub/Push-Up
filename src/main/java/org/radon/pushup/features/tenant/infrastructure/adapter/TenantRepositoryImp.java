package org.radon.pushup.features.tenant.infrastructure.adapter;

import jakarta.transaction.Transactional;
import org.radon.pushup.features.tenant.application.port.out.TenantRepository;
import org.radon.pushup.features.tenant.domain.Tenant;
import org.radon.pushup.features.tenant.domain.TenantStatus;
import org.radon.pushup.features.tenant.infrastructure.mapper.TenantMappers;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantJpaRepository;
import org.radon.pushup.features.tenant.presentation.dto.CreateTenantRequest;
import org.radon.pushup.features.tenant.presentation.dto.TenantResponse;
import org.radon.pushup.features.tenant.presentation.mapper.TenantDtoMapper;
import org.radon.pushup.features.user.infrastructure.repository.UserJpaRepository;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;
import org.radon.pushup.features.user.infrastructure.repository.mapper.UserMappers;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class TenantRepositoryImp implements TenantRepository {

    private final TenantJpaRepository tenantJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public TenantRepositoryImp(TenantJpaRepository tenantJpaRepository, UserJpaRepository userJpaRepository) {
        this.tenantJpaRepository = tenantJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Transactional
    @Override
    public Tenant createTenant(CreateTenantRequest request) {

        tenantJpaRepository.findByName(request.getName())
                .ifPresent(t -> {
                    throw new IllegalArgumentException("Tenant already exists");
                });

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalArgumentException("Unauthenticated");
        }

        String username = auth.getName();

        UserEntity userEntity = userJpaRepository
                .findUserEntityByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        TenantEntity tenant = tenantJpaRepository.save(
                TenantMappers.toTenantEntityFromTenant(
                        new Tenant.Builder()
                                .setName(request.getName())
                                .setStatus(TenantStatus.ACTIVE)
                                .setUsers(Set.of(UserMappers.fromUserEntityToUser(userEntity)))
                                .build()
                )
        );

        userEntity.setTenant(tenant);

        return TenantMappers.toTenantFromTenantEntity(tenant);
    }
}
