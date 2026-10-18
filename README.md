# demo-academico

Sistema académico mínimo (Spring Boot 3.2.5, Java 21, H2 en memoria):
Estudiantes, Programas, Asignaturas, Grupos y Matrículas.

## Cómo ejecutar

```bash
mvn clean package
java -jar target/demo-academico-0.0.1-SNAPSHOT.jar
```

La aplicación escucha en el puerto **8081** (`application.yaml`). Al iniciar, los seeders crean
50 estudiantes, 3 programas, 10 asignaturas y 20 grupos (`app.seed.*`).

Con Docker: `docker build -t demoacademico .` y `docker compose up -d` (publica en el puerto 8080).

## URLs

- Swagger: http://localhost:8081/swagger-ui.html
- H2: http://localhost:8081/h2-console (JDBC URL `jdbc:h2:mem:demoacademico`, usuario `sa`, sin contraseña)

## Módulos

```
co.edu.demoacademico
├── common       api (ApiResponse, ResponseBuilder), exception, controller (GlobalExceptionHandler)
├── estudiantes
├── programas
├── asignaturas
├── grupos
├── matriculas
└── config       seeders
```

## Ports

El módulo `matriculas` no inyecta repositorios de otros módulos; los consulta por contratos públicos:

- `estudiantes.port.EstudianteQueryPort` → implementado por `EstudianteService`
- `grupos.port.GrupoQueryPort` → implementado por `GrupoServiceImpl`

La regla se verifica con `ArquitecturaMatriculasTest` (`mvn test`).
