package org.radon.pushup.features.app.infrastructure.repository;

import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppJpaRepository extends JpaRepository<AppEntity, UUID> {
}
