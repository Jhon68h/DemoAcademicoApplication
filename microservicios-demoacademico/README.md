# microservicios-demoacademico

Dos microservicios Spring Boot 3.2.5 (Java 21, H2 embebido):

- **auth-service** (puerto 8081): usuarios en H2 con contraseñas BCrypt, `POST /auth/login` genera un JWT y `GET /auth/validate` lo valida.
- **consulta-service** (puerto 8082): CRUD de estudiantes; `/api/estudiantes` exige un token válido, que se verifica contra auth-service con OpenFeign.

`demo-academico/` es el proyecto base (puerto 8080) del que se copió consulta-service.

## Ejecutar localmente

```bash
cd auth-service && mvn clean package -DskipTests && java -jar target/auth-service-0.0.1-SNAPSHOT.jar
# en otra terminal
cd consulta-service && mvn clean package -DskipTests && java -jar target/consulta-service-0.0.1-SNAPSHOT.jar
```

Levante primero auth-service: consulta-service lo llama en `services.auth.url` (`http://localhost:8081`).

## Ejecutar con Docker Compose

```bash
(cd auth-service && mvn clean package -DskipTests)
(cd consulta-service && mvn clean package -DskipTests)
docker compose up --build
```

Dentro de Docker, consulta-service usa `SERVICES_AUTH_URL=http://auth-service:8081` (nombre del servicio, no `localhost`).
Para detener: `docker compose down`.

## URLs

| Servicio | Swagger | H2 Console (usuario `sa`, sin contraseña) |
|---|---|---|
| auth-service | http://localhost:8081/swagger-ui.html | http://localhost:8081/h2-console — JDBC `jdbc:h2:mem:authdb` |
| consulta-service | http://localhost:8082/swagger-ui.html | http://localhost:8082/h2-console — JDBC `jdbc:h2:mem:demoacademico` |

## Usuarios de prueba

| Usuario | Contraseña | Rol |
|---|---|---|
| admin | Admin2026* | ADMIN |
| docente | Docente2026* | DOCENTE |
| estudiante1 | Estu2026* | ESTUDIANTE |

## Uso del botón Authorize

1. En el Swagger de auth-service ejecute `POST /auth/login` con un usuario de la tabla.
2. Copie **solo** el valor del campo `token` (sin comillas y sin la palabra `Bearer`).
3. En el Swagger de consulta-service pulse **Authorize**, pegue el token, pulse **Authorize** y luego **Close**.
4. Ejecute `GET /api/estudiantes`: Swagger envía `Authorization: Bearer <token>` automáticamente.

Sin token la respuesta es `401 Token no enviado o formato inválido`; con un token alterado o vencido (1 hora), `401 Token inválido o expirado`.
