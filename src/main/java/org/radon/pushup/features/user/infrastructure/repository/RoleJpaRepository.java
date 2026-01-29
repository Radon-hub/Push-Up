package org.radon.pushup.features.user.infrastructure.repository;

import org.radon.pushup.features.user.infrastructure.repository.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    CharSequence findByName(String name);
}
