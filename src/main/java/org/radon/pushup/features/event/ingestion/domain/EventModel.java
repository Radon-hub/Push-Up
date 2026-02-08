package org.radon.pushup.features.event.ingestion.domain;

import org.radon.pushup.features.app.domain.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EventModel {

    private String eventId;
    private UUID tenantId;
    private UUID appId;
    private String userId;
    private String eventName;
    private String location;
    private String schemaVersion;
    private String appVersion;
    private String device;
    private Platform platform;
    private long eventTime = 0;
    private long receivedAt = 0;
    private Map<String, Object> properties = new HashMap<String, Object>();

    public EventModel() {
    }

    public EventModel(String eventId, UUID tenantId, UUID appId, String userId, String eventName, String location, String schemaVersion, String appVersion, String device, Platform platform, long eventTime, long receivedAt, Map<String, Object> properties) {
        this.eventId = eventId;
        this.tenantId = tenantId;
        this.appId = appId;
        this.userId = userId;
        this.eventName = eventName;
        this.location = location;
        this.schemaVersion = schemaVersion;
        this.appVersion = appVersion;
        this.device = device;
        this.platform = platform;
        this.eventTime = eventTime;
        this.receivedAt = receivedAt;
        this.properties = properties;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(String schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getAppId() {
        return appId;
    }

    public void setAppId(UUID appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public long getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(long receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
