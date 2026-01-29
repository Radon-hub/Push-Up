package org.radon.pushup.features.tenant.domain;

import org.radon.pushup.features.app.domain.App;
import org.radon.pushup.features.user.domain.User;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Tenant {

    private UUID id;
    private String name;
    private TenantStatus status = TenantStatus.DISABLED;
    private Set<User> users;
    private Set<App> apps;
    private Timestamp created_at = new Timestamp(System.currentTimeMillis());
    private Timestamp updated_at = new Timestamp(System.currentTimeMillis());


    public Tenant() {}

    private Tenant(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.status = builder.status;
        this.users = builder.users;
        this.apps = builder.apps;
        this.created_at = builder.created_at;
        this.updated_at = builder.updated_at;
    }

    public static class Builder {
        private UUID id;
        private String name;
        private TenantStatus status;
        private Set<User> users;
        private Set<App> apps;
        private Timestamp created_at = new Timestamp(System.currentTimeMillis());
        private Timestamp updated_at = new Timestamp(System.currentTimeMillis());

        public Builder setId(UUID id) {
            this.id = id;
            return this;
        }
        public Builder setName(String name) {
            this.name = name;
            return this;
        }
        public Builder setStatus(TenantStatus status) {
            this.status = status;
            return this;
        }
        public Builder setUsers(Set<User> users) {
            this.users = users;
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
        public Tenant build() {
            return new Tenant(this);
        }
    }

    public Tenant(UUID id, String name, TenantStatus status, Set<User> users, Set<App> apps, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.users = users;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }
}
