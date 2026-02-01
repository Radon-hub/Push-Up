package org.radon.pushup.features.app.infrastructure.repository;

import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppJpaRepository extends JpaRepository<AppEntity, UUID> {
    Optional<AppEntity> findByNameAndTenant(String name, TenantEntity tenant);
}
