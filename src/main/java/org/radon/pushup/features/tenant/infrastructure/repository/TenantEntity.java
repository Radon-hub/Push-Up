package org.radon.pushup.features.tenant.infrastructure.repository;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.tenant.domain.TenantStatus;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private TenantStatus status;
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<AppEntity> apps;
    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<UserEntity> users;
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());
}
