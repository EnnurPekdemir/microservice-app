# Microservice Architecture

This is a multi-module Spring Cloud microservice project.

## Modules
- **config-server**: Spring Cloud Config Server managing centralized configuration.
- **eureka-server**: Netflix Eureka discovery server for service registry.
- **service-parent**: Common parent POM managing shared dependencies and configurations.
- **account-service**: Account management microservice backed by Apache Cassandra.
- **ticket-service**: Ticket management microservice backed by MySQL (Spring Data JPA) and Elasticsearch (Spring Data Elasticsearch), communicating with `account-service` via OpenFeign, and publishing ticket events to RabbitMQ.
- **notification-service**: Event-driven notification microservice consuming messages from RabbitMQ using Spring Cloud Stream.
- **api-gateway**: Spring Cloud Gateway routing requests to microservices with CircuitBreaker (Resilience4j) fallback handling.
- **admin-server**: Spring Boot Admin Server monitoring health, metrics, and logs of all microservices via Eureka.

## Port Map
| Service | Port | Description |
|---|---|---|
| config-server | 8888 | Spring Cloud Config Server |
| eureka-server | 8500 | Netflix Eureka Service Discovery |
| api-gateway | 8090 | Spring Cloud API Gateway |
| admin-server | 8095 | Spring Boot Admin Web UI |
| account-service | 8080 | Account Microservice |
| ticket-service | 8085 | Ticket Microservice |
| notification-service | 8086 | Notification Microservice |
| zipkin | 9411 | Distributed Tracing Web UI |
| rabbitmq | 15672 | RabbitMQ Management Web UI (AMQP: 5672) |
| cassandra-web | 3000 | Cassandra Web Admin UI (DB: 9042) |
| elasticsearch | 9200 | Elasticsearch REST API |
| mysql-db | 3306 | MySQL Database |

## Build & Run

### 1. Build All Microservices
```bash
./mvnw clean package -DskipTests
```

### 2. Run with Docker Compose
```bash
docker-compose up --build -d
```

