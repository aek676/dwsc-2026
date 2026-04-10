# Proyecto DWSC - Actividad 6

Microservicios Spring Boot con servidor de configuración y bases de datos PostgreSQL.

## Estructura del Proyecto

```
actividad6/
├── config-server/        # Servidor de configuración (puerto 8888)
├── manager-users/       # Microservicio de usuarios (puerto 8081)
├── manager-students/   # Microservicio de estudiantes (puerto 8082)
├── config-repo/        # Repositorio de configuración
└── docker-compose.yml # Contenedores PostgreSQL
```

## Requisitos Previos

- Java 17+
- Docker y Docker Compose

## Ejecución

### 1. Iniciar las bases de datos

```bash
docker-compose up -d
```

Esto crea dos contenedores PostgreSQL:

- **postgres-users**: puerto 5432, BD `dwsc_users`
- **postgres-students**: puerto 5433, BD `dwsc_students`

### 2. Compilar los microservicios

Ejecutar en cada directorio:

```bash
cd config-server && ./mvnw clean package -DskipTests
cd manager-users && ./mvnw clean package -DskipTests
cd manager-students && ./mvnw clean package -DskipTests
```

### 3. Ejecutar los servicios

Iniciar en el siguiente orden:

```bash
# Terminal 1: Servidor de configuración
./mvnw spring-boot:run

# Terminal 2: Microservicio de usuarios
./mvnw spring-boot:run

# Terminal 3: Microservicio de estudiantes
./mvnw spring-boot:run
```

## Puertos

| Servicio            | Puerto |
| ------------------- | ------ |
| Config Server       | 8888   |
| Manager Users       | 8081   |
| Manager Students    | 8082   |
| PostgreSQL Users    | 5432   |
| PostgreSQL Students | 5433   |

## Credenciales

- **Usuario BD**: `estudiante`
- **Password BD**: `estudiante`

## Endpoints

- Config Server:
  - `http://localhost:8888/actuator/health`
  - `http://localhost:8888/application/default`
  - `http://localhost:8888/manager-users/default`
  - `http://localhost:8888/manager-students/default`

- Users API: `http://localhost:8081/api/users`
- Students API: `http://localhost:8082/api/students`

## Detener los servicios

```bash
# Detener microservicios (Ctrl+C en cada terminal)
docker-compose down
```
