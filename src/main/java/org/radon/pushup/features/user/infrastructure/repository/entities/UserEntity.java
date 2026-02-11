package org.radon.pushup.features.user.infrastructure.repository.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;
import org.radon.pushup.features.tenant.infrastructure.repository.TenantEntity;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private TenantEntity tenant;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "apps_users_table",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "app_id")
    )
    private Set<AppEntity> apps;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity role;
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());

    public UserEntity(String username, String password, String email, String phone, RoleEntity role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public UserEntity(String username, String password, String email, String phone, TenantEntity tenant, RoleEntity role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.tenant = tenant;
        this.role = role;
    }
}
