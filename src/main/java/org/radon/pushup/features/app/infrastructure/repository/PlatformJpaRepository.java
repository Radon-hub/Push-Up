package org.radon.pushup.features.app.infrastructure.repository;

import org.radon.pushup.features.app.domain.Platform;
import org.radon.pushup.features.app.infrastructure.repository.entities.PlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlatformJpaRepository extends JpaRepository<PlatformEntity, Long> {
    Optional<PlatformEntity> findByPlatform(Platform platform);
}
