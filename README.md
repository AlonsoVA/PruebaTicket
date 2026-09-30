# PruebaTicket

API REST de práctica con Java 21, Spring Boot 4.1.1 y PostgreSQL.

## Stack

- Java 21
- Spring Web, Spring Data JPA, Validation y Lombok
- PostgreSQL 16 (Docker Compose)
- H2 solo para tests

## Arranque

1. Levantar PostgreSQL:

```bash
docker compose up -d
```

2. Compilar y ejecutar:

```bash
./mvnw spring-boot:run
```

La API queda en `http://localhost:8080`.

## Tests

```bash
./mvnw test
```

Los tests usan H2 en memoria y no necesitan PostgreSQL.
