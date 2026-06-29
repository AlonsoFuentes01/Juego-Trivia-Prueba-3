# Trivia Game — Arquitectura de Microservicios

Proyecto semestral de la asignatura **Desarrollo FullStack 1 (DSY1103)**.  
Sistema de juego de trivia desarrollado con Spring Boot bajo una arquitectura distribuida de microservicios.

---

## Integrantes

| Nombre | Rol |
|--------|-----|
| Alonso Fuentes | Desarrollador |
| Fabricio Canova | JEFE Desarrolador(El Patron) |
| Harold Morales | Desarrollador |

---

## Descripción del proyecto

Trivia Game es una aplicación de preguntas y respuestas. Los jugadores pueden iniciar partidas, responder preguntas de opción múltiple, acumular puntos y consultar un ranking general. El sistema está construido sobre una arquitectura de microservicios independientes que se comunican mediante REST a través de un API Gateway centralizado, con registro y descubrimiento de servicios vía Eureka.

---

## Microservicios implementados

| Microservicio | Puerto | Descripción |
|---------------|--------|-------------|
| `eureka` | 8761 | Servidor de descubrimiento de servicios (Eureka Server) |
| `gateway` | 8080 | API Gateway centralizado (Spring Cloud Gateway) |
| `jugadores` | dinámico | Gestión de jugadores, armas y ranking |
| `partidas` | dinámico | Gestión de partidas, niveles y jefes |
| `preguntas` | dinámico | Gestión de preguntas, respuestas y respuestas de jugador |

> Los microservicios de negocio usan puerto dinámico (`port: 0`) y se registran en Eureka. Todo el tráfico pasa por el Gateway en el puerto 8080.

---

## Rutas principales del Gateway

Base URL: `http://localhost:8080`

| Prefijo | Servicio destino |
|---------|-----------------|
| `/api/v1/jugadores/**` | jugadores |
| `/api/v1/armas/**` | jugadores |
| `/api/v1/partidas/**` | partidas |
| `/api/v1/jefes/**` | partidas |
| `/api/v1/niveles/**` | partidas |
| `/api/v1/preguntas/**` | preguntas |
| `/api/v1/respuestas/**` | preguntas |
| `/api/v1/respuestas-jugador/**` | preguntas |

---

## Documentación Swagger / OpenAPI

La documentación está centralizada en el Gateway:

- **Swagger UI unificado:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- API Docs jugadores: [http://localhost:8080/jugadores/api-docs](http://localhost:8080/jugadores/api-docs)
- API Docs partidas: [http://localhost:8080/partidas/api-docs](http://localhost:8080/partidas/api-docs)
- API Docs preguntas: [http://localhost:8080/preguntas/api-docs](http://localhost:8080/preguntas/api-docs)

---

## Instrucciones de ejecución local

### Requisitos previos

- Java 21+
- Maven 3.8+
- MySQL 8+ en ejecución local
- (Opcional) IntelliJ IDEA o VS Code

### Paso 1 — Crear las bases de datos

```sql
CREATE DATABASE db_jugadores_dev;
CREATE DATABASE db_partidas_dev;
CREATE DATABASE db_preguntas_dev;
```

### Paso 2 — Iniciar los servicios en orden

**Opción A — Script automático**

En Windows:
```bash
iniciar-todo.bat
```

En Linux/Mac:
```bash
chmod +x iniciar-todo.sh
./iniciar-todo.sh
```

**Opción B — Manual (en este orden)**

```bash
# 1. Eureka
cd eureka
./mvnw spring-boot:run

# 2. Microservicios de negocio (en terminales separadas)
cd jugadores
./mvnw spring-boot:run

cd partidas
./mvnw spring-boot:run

cd preguntas
./mvnw spring-boot:run

# 3. Gateway (último)
cd gateway
./mvnw spring-boot:run
```

### Paso 3 — Verificar que todo esté operativo

- Eureka Dashboard: [http://localhost:8761](http://localhost:8761)
- Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Ejemplo de flujo de juego (Postman)

```
# Ver jugadores disponibles
GET http://localhost:8080/api/v1/jugadores

# Iniciar una partida
POST http://localhost:8080/api/v1/partidas/iniciar?idJugador=1

# Ver preguntas disponibles
GET http://localhost:8080/api/v1/preguntas

# Responder una pregunta
POST http://localhost:8080/api/v1/respuestas-jugador/responder?idPartida=1&idRespuesta=1

# Ver ranking
GET http://localhost:8080/api/v1/jugadores/ranking
```

---

## Tecnologías utilizadas

- **Spring Boot 3** — framework principal
- **Spring Cloud Gateway** — API Gateway
- **Spring Cloud Netflix Eureka** — descubrimiento de servicios
- **Spring WebFlux / WebClient** — comunicación REST entre microservicios
- **Spring Data JPA + MySQL** — persistencia
- **Flyway** — migraciones de base de datos
- **Springdoc OpenAPI / Swagger UI** — documentación técnica
- **JUnit 5 + Mockito** — pruebas unitarias
- **Maven** — gestión de dependencias
