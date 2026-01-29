package org.radon.pushup.features.app.domain;

import org.radon.pushup.features.tenant.domain.Tenant;

import java.sql.Timestamp;
import java.util.UUID;

public class App {
    private UUID id;
    private Tenant tenant;
    private String api_key;
    private String name;
    private String platform;
    private AppStatus status;
    private Timestamp created_at;
    private Timestamp updated_at;

    public App() {}

    public App(UUID id, Tenant tenant, String api_key, String name, String platform, AppStatus status, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.tenant = tenant;
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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public AppStatus getStatus() {
        return status;
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
