package org.radon.pushup.features.app.domain;

import org.radon.pushup.features.tenant.domain.Tenant;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

public class App {
    private UUID id;
    private Set<ApiKey> api_key;
    private String name;
    private Set<Platform> platform;
    private AppStatus status;
    private Timestamp created_at;
    private Timestamp updated_at;

    public App() {}

    public App(UUID id, Set<ApiKey> api_key, String name, Set<Platform> platform, AppStatus status, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.api_key = api_key;
        this.name = name;
        this.platform = platform;
        this.status = status;
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

    public AppStatus getStatus() {
        return status;
    }

    public Set<ApiKey> getApi_key() {
        return api_key;
    }

    public void setApi_key(Set<ApiKey> api_key) {
        this.api_key = api_key;
    }

    public Set<Platform> getPlatform() {
        return platform;
    }

    public void setPlatform(Set<Platform> platform) {
        this.platform = platform;
    }

    public void setStatus(AppStatus status) {
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
}
