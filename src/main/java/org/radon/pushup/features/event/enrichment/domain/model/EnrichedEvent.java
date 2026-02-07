package org.radon.pushup.features.event.enrichment.domain.model;

import org.radon.pushup.features.app.domain.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


public class EnrichedEvent {

    private final String eventId;
    private final UUID tenantId;
    private final UUID appId;
    private final String userId;
    private final String eventName;
    private final String eventNormalized;
    private final String location;
    private final Platform platform;
    private final String version;
    private final long eventTime;
    private final String eventDate;
    private final Integer eventHour;
    private final long receivedAt;
    private final Map<String, Object> properties;

    public EnrichedEvent(
    ) {
        this.eventId = "";
        this.tenantId = UUID.randomUUID();
        this.appId = UUID.randomUUID();
        this.userId = "";
        this.eventName = "";
        this.eventNormalized = "";
        this.location = "";
        this.platform = Platform.WEB;
        this.version = "";
        this.eventTime = 0;
        this.eventDate = "";
        this.eventHour = 0;
        this.receivedAt = 0;
        this.properties = new HashMap<>();
    }

    private EnrichedEvent(Builder builder) {
        this.userId = builder.userId;
        this.eventId = builder.eventId;
        this.tenantId = builder.tenantId;
        this.appId = builder.appId;
        this.eventName = builder.eventName;
        this.eventNormalized = builder.eventNormalized;
        this.location = builder.location;
        this.platform = builder.platform;
        this.version = builder.version;
        this.eventTime = builder.eventTime;
        this.eventDate = builder.eventDate;
        this.eventHour = Objects.requireNonNullElse(builder.eventHour, 0);
        this.receivedAt = builder.receivedAt;
        this.properties = builder.properties;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder()
                .eventDate(this.eventDate)
                .eventHour(this.eventHour)
                .tenantId(this.tenantId)
                .appId(this.appId)
                .eventId(this.eventId)
                .eventName(this.eventName)
                .eventNormalized(this.eventNormalized)
                .userId(this.userId)
                .eventTime(this.eventTime)
                .receivedAt(this.receivedAt)
                .platform(this.platform)
                .location(this.location)
                .version(this.version)
                .properties(this.properties);
    }

    public static class Builder {

        private String eventId;
        private UUID tenantId;
        private UUID appId;
        private String userId;
        private String eventName;
        private String eventNormalized;
        private String location;
        private Platform platform;
        private String version;
        private long eventTime;
        private String eventDate;
        private Integer eventHour;
        private long receivedAt;
        private Map<String, Object> properties;

        public Builder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
        public Builder tenantId(UUID tenantId) {
            this.tenantId = tenantId;
            return this;
        }
        public Builder appId(UUID appId) {
            this.appId = appId;
            return this;
        }
        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }
        public Builder eventName(String eventName) {
            this.eventName = eventName;
            return this;
        }
        public Builder eventNormalized(String eventNormalized) {
            this.eventNormalized = eventNormalized;
            return this;
        }
        public Builder location(String location) {
            this.location = location;
            return this;
        }
        public Builder platform(Platform platform) {
            this.platform = platform;
            return this;
        }
        public Builder version(String version) {
            this.version = version;
            return this;
        }
        public Builder eventTime(long eventTime) {
            this.eventTime = eventTime;
            return this;
        }
        public Builder eventDate(String eventDate) {
            this.eventDate = eventDate;
            return this;
        }
        public Builder eventHour(int eventHour) {
            this.eventHour = eventHour;
            return this;
        }
        public Builder receivedAt(long receivedAt) {
            this.receivedAt = receivedAt;
            return this;
        }
        public Builder properties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }
        public EnrichedEvent build() {
            return new EnrichedEvent(this);
        }

    }



    public String getEventId() {
        return eventId;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getAppId() {
        return appId;
    }

    public String getUserId() {
        return userId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventNormalized() {
        return eventNormalized;
    }

    public String getLocation() {
        return location;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getVersion() {
        return version;
    }

    public long getEventTime() {
        return eventTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public Integer getEventHour() {
        return eventHour;
    }

    public long getReceivedAt() {
        return receivedAt;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
