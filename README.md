# Cold Chain Monitoring — Backend (Spring Boot)

## Tech
- Java 21
- Spring Boot
- MySQL
- Spring Web, Spring Data JPA
- Lombok

## Setup MySQL
Create a MySQL user/db or just keep `createDatabaseIfNotExist=true` in the JDBC URL.

Edit:
`src/main/resources/application.properties`
and set:
- `spring.datasource.username`
- `spring.datasource.password`

## Run
```bash
mvn spring-boot:run
```

## Auth (simple)
This project uses a simple token (header `X-Auth-Token`) to avoid over-engineering.

Seed users:
- admin / admin123
- user1 / user123

Login:
`POST http://localhost:8080/api/login`
Body:
```json
{"username":"admin","password":"admin123"}
```

Use returned token in all other requests:
Header:
`X-Auth-Token: <token>`

## Endpoints
- POST /api/login
- POST /api/measurements
- GET  /api/measurements
- GET  /api/measurements/latest
- GET  /api/measurements/export/csv
- GET  /api/tickets
- POST /api/tickets
- PUT  /api/tickets/{id}/close
- GET  /api/auditlogs

## IoT payload example
ESP8266 can send JSON like:
```json
{"temperature": 5.3, "humidity": 61.0, "timestamp":"2025-12-25T12:00:00Z"}
```
If `timestamp` is absent, server uses current time.
