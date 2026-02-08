package org.radon.pushup.features.event.ingestion.domain;


public class DlqEvent {

    private final String appId;
    private final String tenantId;
    private final String schemaVersion;
    private final String appVersion;
    private final String device;
    private final EventStages stage;
    private final String errorCode;
    private final String errorMessage;
    private final Object originalEvent;
    private final long receivedAt;

    private DlqEvent(Builder builder){
        this.appId = builder.appId;
        this.tenantId = builder.tenantId;
        this.stage = builder.stage;
        this.errorCode = builder.errorCode;
        this.errorMessage = builder.errorMessage;
        this.originalEvent = builder.originalEvent;
        this.schemaVersion = builder.schemaVersion;
        this.appVersion = builder.appVersion;
        this.device = builder.device;
        this.receivedAt = builder.receivedAt;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {

        private String appId;
        private String tenantId;

        private EventStages stage;
        private String errorCode;
        private String errorMessage;

        private String schemaVersion;
        private String appVersion;
        private String device;

        private Object originalEvent;
        private long receivedAt;


        public Builder appId(String appId) {
            this.appId = appId;
            return this;
        }
        public Builder tenantId(String tenantId) {
            this.tenantId = tenantId;
            return this;
        }
        public Builder stage(EventStages stage) {
            this.stage = stage;
            return this;
        }
        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }
        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }
        public Builder originalEvent(Object originalEvent) {
            this.originalEvent = originalEvent;
            return this;
        }
        public Builder schemaVersion(String schemaVersion) {
            this.schemaVersion = schemaVersion;
            return this;
        }
        public Builder appVersion(String appVersion) {
            this.appVersion = appVersion;
            return this;
        }
        public Builder device(String device) {
            this.device = device;
            return this;
        }
        public Builder receivedAt(long receivedAt) {
            this.receivedAt = receivedAt;
            return this;
        }
        public DlqEvent build() {
            return new DlqEvent(this);
        }

    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getDevice() {
        return device;
    }

    public String getAppId() {
        return appId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public EventStages getStage() {
        return stage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getOriginalEvent() {
        return originalEvent;
    }

    public long getReceivedAt() {
        return receivedAt;
    }
}
