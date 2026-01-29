package org.radon.pushup.features.tenant.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TenantJpaRepository extends JpaRepository<TenantEntity, UUID> {
    Optional<TenantEntity> findByName(String name);
    @Query(value = """
        SELECT t.* from tenants t
        join users u on t.id = u.tenant_id
        join roles r on u.role_id = r.id where r.name = 'OWNER' and u.username = 'alireza.kh'
    """,
    nativeQuery = true
    )
    Optional<TenantEntity> findByOwnerUsersName(String usersName);
}
