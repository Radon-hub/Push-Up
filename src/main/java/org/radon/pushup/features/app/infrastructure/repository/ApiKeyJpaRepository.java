package org.radon.pushup.features.app.infrastructure.repository;

import org.radon.pushup.features.app.infrastructure.repository.entities.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyJpaRepository extends JpaRepository<ApiKeyEntity,Long> {
}
