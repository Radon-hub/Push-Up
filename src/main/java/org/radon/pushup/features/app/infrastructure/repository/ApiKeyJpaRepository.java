package org.radon.pushup.features.app.infrastructure.repository;

import org.radon.pushup.features.app.infrastructure.repository.entities.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApiKeyJpaRepository extends JpaRepository<ApiKeyEntity,Long> {
    @Query(value = """
    select * from api_keys where api_key = :apiKey
""",nativeQuery = true)
    ApiKeyEntity findByApiKey(String apiKey);
}
