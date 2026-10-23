# Microservice Architecture

This is a multi-module Spring Cloud microservice project.

## Modules
- **config-server**: Spring Cloud Config Server managing centralized configuration.
- **eureka-server**: Netflix Eureka discovery server for service registry.
- **service-parent**: Common parent POM managing shared dependencies and configurations.
- **account-service**: Account management microservice backed by Apache Cassandra.
- **ticket-service**: Ticket management microservice backed by MySQL (Spring Data JPA) and Elasticsearch (Spring Data Elasticsearch), communicating with `account-service` via OpenFeign, and publishing ticket events to RabbitMQ.
- **notification-service**: Event-driven notification microservice consuming messages from RabbitMQ using Spring Cloud Stream.

