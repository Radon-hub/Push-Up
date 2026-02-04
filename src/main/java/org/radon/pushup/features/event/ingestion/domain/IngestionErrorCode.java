package org.radon.pushup.features.event.ingestion.domain;

public enum IngestionErrorCode {
    INVALID_SCHEMA,
    DUPLICATE_EVENT,
    APP_SUSPENDED,
    SERIALIZATION_ERROR,
    KAFKA_PUBLISH_FAILED,
    INTERNAL_ERROR
}

