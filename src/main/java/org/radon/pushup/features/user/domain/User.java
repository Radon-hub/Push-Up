package org.radon.pushup.features.user.domain;

import org.jspecify.annotations.Nullable;
import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.user.infrastructure.repository.entities.RoleEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

public class User implements UserDetails {

    private UUID id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private Role role;
    private Set<App> apps;
    private Timestamp created_at;
    private Timestamp updated_at;

    public User() {}

    public static class Builder {
        private UUID id;
        private String username;
        private String password;
        private String email;
        private String phone;
        private Role role;
        private Set<App> apps;
        private Timestamp created_at;
        private Timestamp updated_at;

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }
        public Builder setApps(Set<App> apps) {
            this.apps = apps;
            return this;
        }
        public Builder setCreated_at(Timestamp created_at) {
            this.created_at = created_at;
            return this;
        }
        public Builder setUpdated_at(Timestamp updated_at) {
            this.updated_at = updated_at;
            return this;
        }
        public User build(){
            return new User(this);
        }
    }

    public User(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.phone = builder.phone;
        this.role = builder.role;
        this.apps = builder.apps;
        this.created_at = builder.created_at;
        this.updated_at = builder.updated_at;
    }

    public User(String username, String password, String email, String phone, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public User(UUID id, String username, String password, String email, String phone, Role role, Set<App> apps, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.apps = apps;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        var roleAndAuthorities = new ArrayList<GrantedAuthority>();

        roleAndAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.role.getRole()));
        roleAndAuthorities.addAll(role.getAuthorities());

        return roleAndAuthorities;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
