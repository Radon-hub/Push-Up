package org.radon.pushup.features.user.infrastructure.repository.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_authority_table",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<AuthorityEntity> authorities;
    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserEntity> users;

    public RoleEntity(String name, Set<AuthorityEntity> authorities) {
        this.name = name;
        this.authorities = authorities;
    }

    public RoleEntity(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

}
