package org.radon.pushup.features.app.infrastructure.repository.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "apps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String api_key;
    @Column(unique = true)
    private String name;
    private String platform;
    @Enumerated(EnumType.STRING)
    private AppStatus status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "apps_users_table",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "app_id")
    )
    private Set<UserEntity> users;
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());
}
