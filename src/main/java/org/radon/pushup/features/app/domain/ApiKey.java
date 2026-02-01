package org.radon.pushup.features.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.radon.pushup.features.app.infrastructure.repository.entities.AppEntity;

import java.sql.Timestamp;

public class ApiKey {

    private Long id;
    private String api_key;
    private String api_key_prefix;
    private ApiKeyStatus api_key_status;
    private AppEntity app;
    private Timestamp created_at;
    private Timestamp updated_at;

    public ApiKey(Long id, String api_key, String api_key_prefix, ApiKeyStatus api_key_status, AppEntity app, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.api_key = api_key;
        this.api_key_prefix = api_key_prefix;
        this.api_key_status = api_key_status;
        this.app = app;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public ApiKey(String api_key, String api_key_prefix, ApiKeyStatus api_key_status, AppEntity app) {
        this.api_key = api_key;
        this.api_key_prefix = api_key_prefix;
        this.api_key_status = api_key_status;
        this.app = app;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_key_prefix() {
        return api_key_prefix;
    }

    public void setApi_key_prefix(String api_key_prefix) {
        this.api_key_prefix = api_key_prefix;
    }

    public ApiKeyStatus getApi_key_status() {
        return api_key_status;
    }

    public void setApi_key_status(ApiKeyStatus api_key_status) {
        this.api_key_status = api_key_status;
    }

    public AppEntity getApp() {
        return app;
    }

    public void setApp(AppEntity app) {
        this.app = app;
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
