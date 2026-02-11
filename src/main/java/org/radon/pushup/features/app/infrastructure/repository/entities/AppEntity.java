package org.radon.pushup.features.app.infrastructure.repository.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.pushup.features.app.domain.AppStatus;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;
import org.radon.pushup.features.user.infrastructure.repository.entities.UserEntity;

import java.sql.Timestamp;
import java.util.HashSet;
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
    @OneToMany(mappedBy = "app",cascade = CascadeType.ALL, orphanRemoval = true,fetch =  FetchType.LAZY)
    private Set<ApiKeyEntity> api_key = new HashSet<>();
    @Column(unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "apps_platforms_table",
            joinColumns = @JoinColumn(name = "app_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private Set<PlatformEntity> platform = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private AppStatus status = AppStatus.DISABLED;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;
    @ManyToMany(mappedBy = "apps",fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());

    public AppEntity(String name, TenantEntity tenant) {
        this.name = name;
        this.tenant = tenant;
    }

}
