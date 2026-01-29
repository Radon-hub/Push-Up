package org.radon.pushup.features.user.infrastructure.repository;

import org.radon.pushup.features.user.infrastructure.repository.entities.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityJpaRepository extends JpaRepository<AuthorityEntity, Integer> {
}
