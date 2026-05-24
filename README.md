<p align="center">
  <img src="assets/pushup-logo.png" width="160"/>
</p>

# PushUp

> A MoEngage-inspired multi-tenant event analytics and engagement infrastructure built with Spring Boot, Kafka, ClickHouse, PostgreSQL, and S3-compatible storage.

PushUp is a backend platform designed to ingest, process, enrich, store, and analyze high-volume application events in real time. It provides a scalable event pipeline for mobile and web applications, enabling analytics, user behavior tracking, and the foundation for future customer engagement features such as segmentation and push notifications.

---

## Why PushUp?

Modern applications generate a massive amount of behavioral data:

* User logins
* Screen views
* Purchases
* Clicks and interactions
* Custom business events

PushUp was designed to solve a common problem:

> How can we reliably collect billions of events, process them in real time, and make them queryable for analytics while remaining scalable and multi-tenant?

The platform is inspired by systems such as MoEngage, Mixpanel, and customer engagement infrastructures used in modern SaaS products.

---

## Core Features

### Multi-Tenant Architecture

* Tenant-based isolation
* Multiple applications per tenant
* Role-based access system
* Secure API-key authentication
* API key rotation and revocation

### Real-Time Event Ingestion

* High-throughput event ingestion API
* Kafka-backed event streaming
* Tenant-aware event routing
* Event validation and normalization
* Idempotency handling

### Event Enrichment Pipeline

Incoming events are enriched before persistence:

* Event normalization
* Time derivation (`event_date`, `event_hour`)
* Platform and version enrichment
* Metadata standardization
* Schema consistency enforcement

### Fault-Tolerant Processing

Dedicated Dead Letter Queues (DLQ) for:

* Ingestion failures
* Enrichment failures
* Storage failures

No event is silently dropped.

### Real-Time Analytics

Analytics powered by ClickHouse:

* Event counts
* Time-series analytics
* User timelines
* Funnel analysis
* Retention analysis
* Breakdowns by dimensions and properties

### Scalable Storage Strategy

Hot and cold storage architecture:

* **Hot storage:** ClickHouse for real-time analytics
* **Cold storage:** S3 / MinIO for historical archival
* Replay-safe event pipeline
* Long-term event retention

### Dockerized Infrastructure

The entire platform runs through Docker:

* Spring Boot services
* Kafka
* Zookeeper
* ClickHouse
* PostgreSQL
* MinIO

---

## High-Level Architecture

```text
                        ┌────────────────────┐
                        │   Client SDK/API   │
                        └──────────┬─────────┘
                                   │
                                   ▼
                    ┌──────────────────────────┐
                    │ Event Ingestion Service  │
                    │      (Spring Boot)       │
                    └──────────┬───────────────┘
                               │
                               ▼
                      ┌────────────────┐
                      │ Kafka (Raw)    │
                      │ events.raw.v1  │
                      └───────┬────────┘
                              │
                              ▼
                   ┌──────────────────────────┐
                   │   Enrichment Service     │
                   │      (Spring Boot)       │
                   └──────────┬───────────────┘
                              │
                              ▼
                   ┌──────────────────────────┐
                   │ Kafka (Enriched Events)  │
                   │ events.enriched.v1       │
                   └──────────┬───────────────┘
                              │
                              ▼
                 ┌─────────────────────────────┐
                 │ ClickHouse Kafka Engine     │
                 │ + Materialized Views        │
                 └──────────┬──────────────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │ ClickHouse       │
                    │ Analytics Store  │
                    └────────┬─────────┘
                             │
              ┌──────────────┴──────────────┐
              ▼                             ▼
      ┌───────────────┐           ┌────────────────┐
      │ Analytics API │           │ S3 / MinIO     │
      │ (Spring Boot) │           │ Cold Storage   │
      └───────────────┘           └────────────────┘
```

---

## Event Processing Pipeline

### 1. Event Ingestion

Applications send events using an API key.

Example payload:

```json
{
  "event": "purchase",
  "userId": "user_123",
  "timestamp": 1748123123,
  "properties": {
    "price": 120,
    "currency": "EUR"
  }
}
```

The ingestion layer:

* Validates API keys
* Resolves tenant and application
* Validates event schema
* Publishes events to Kafka

Topic:

```text
events.raw.v1
```

---

### 2. Event Enrichment

Raw events pass through an enrichment pipeline.

Enrichment responsibilities:

* Event normalization
* Metadata enrichment
* Timestamp derivation
* Schema stabilization
* Analytics readiness

Output topic:

```text
events.enriched.v1
```

---

### 3. Analytics Storage

Enriched events are consumed directly by ClickHouse using Kafka Engine tables and Materialized Views.

This enables:

* Near real-time ingestion
* High throughput processing
* Query-ready analytics

The main event table includes:

* Tenant ID
* App ID
* Event ID
* User ID
* Event name
* Normalized event
* Platform metadata
* Event timestamp
* JSON properties

---

### 4. Cold Data Archival

Older event data is archived to S3-compatible storage (MinIO).

Benefits:

* Reduced ClickHouse storage costs
* Long-term retention
* Replay capabilities
* Historical recovery

Replay architecture:

```text
S3 / MinIO → Kafka → Enrichment → ClickHouse
```

---

## Analytics Capabilities

PushUp currently supports:

### Event Counts

Analyze event frequency over time.

Examples:

* Daily active events
* Hourly event volume
* Custom event tracking

### Time-Series Analytics

Track event trends across:

* Hour
* Day
* Week
* Custom ranges

### Funnel Analysis

Analyze user progression through steps.

Example funnel:

```text
View Product → Add To Cart → Purchase
```

### Retention Analysis

Measure user return behavior.

Examples:

* Day 1 retention
* Day 7 retention
* Day 30 retention

### User Timelines

Inspect complete event history for a user.

---

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Kafka
* Spring Data JPA

### Data Infrastructure

* PostgreSQL → transactional data
* ClickHouse → analytical database
* Apache Kafka → event streaming
* MinIO / S3 → cold storage

### Infrastructure

* Docker
* Docker Compose

---

## Database Responsibilities

### PostgreSQL

Used for transactional and relational data:

* Tenants
* Applications
* Users
* Roles & permissions
* API keys
* Access management

### ClickHouse

Used for analytics:

* Event storage
* Aggregations
* Funnels
* Retention
* Time-series analytics

### S3 / MinIO

Used for:

* Historical event storage
* Data replay
* Cold retention

---

## Project Structure

```text
pushup/
├── ingestion-service/
├── enrichment-service/
├── analytics-service/
├── common/
├── docker/
├── postgres/
├── clickhouse/
├── kafka/
├── minio/
├── postman-collection/
└── docker-compose.yml
```

---

## Running Locally

### Prerequisites

* Docker
* Docker Compose
* Java 21+
* Maven

### Clone Repository

```bash
git clone <your-repository-url>
cd pushup
```

### Start Infrastructure

```bash
docker compose up -d
```

This will start:

* PostgreSQL
* Kafka
* Zookeeper
* ClickHouse
* MinIO
* Spring Boot services

---

## API Collection

A Postman collection is included for testing:

* Tenant management
* App management
* API key lifecycle
* Event ingestion
* Analytics endpoints

Import the collection from:

```text
/postman-collection
```

---

## Scalability Decisions

PushUp was designed with scalability in mind:

### Kafka-Based Architecture

Provides:

* Decoupled services
* Replayability
* High throughput
* Fault tolerance

### ClickHouse for Analytics

Chosen because of:

* Columnar storage
* Fast aggregations
* Time-series performance
* Massive event scalability

### Hot / Cold Storage

Balances:

* Query performance
* Cost optimization
* Historical retention

---

## Current Status

### Implemented

* Multi-tenant system
* Tenant apps & API keys
* API key rotation / revocation
* Event ingestion
* Kafka event streaming
* Enrichment pipeline
* Dead Letter Queues
* ClickHouse analytics storage
* Materialized views
* Analytics APIs
* Dockerized infrastructure
* S3 / MinIO cold storage
* Postman API collection

### Planned

* User segmentation engine
* Push notification delivery
* Campaign builder
* Audience targeting
* Real-time triggers
* Dashboard UI

---

## Roadmap

### Phase 1 — Analytics Infrastructure

* [x] Event ingestion
* [x] Kafka streaming
* [x] Event enrichment
* [x] ClickHouse analytics
* [x] Dockerization
* [x] Cold storage

### Phase 2 — Intelligence Layer

* [ ] User profiles
* [ ] Segmentation engine
* [ ] Behavioral targeting

### Phase 3 — Customer Engagement

* [ ] Push notifications
* [ ] Campaign orchestration
* [ ] Triggered journeys
* [ ] Scheduling system

---

## License

This project is currently under active development.

License details will be added in the future.

---

## Author

Built as a deep backend engineering project focused on distributed systems, event processing, analytics infrastructure, and customer engagement architecture.
