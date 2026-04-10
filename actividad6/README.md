# Actividad 6 - Sistema de Gestión con Microservicios y Spring Cloud Config

## Requisitos Previos

- Java 17
- Docker y Docker Compose

## Estructura del Proyecto

```
actividad6/
├── config-server/          # Servidor de configuración (puerto 8888)
├── manager-users/          # Microservicio de usuarios (puerto 8081)
├── manager-students/       # Microservicio de estudiantes (puerto 8082)
├── config-repo/            # Repositorio de configuración
├── docker-compose.yml      # Bases de datos PostgreSQL
└── init-*.sql              # Scripts de inicialización
```

## Pasos de Ejecución

### 1. Levantar las Bases de Datos

```bash
cd actividad6
docker-compose up -d
```

Esto crea dos contenedores PostgreSQL:

- **Usuarios**: puerto 5432, BD `dwsc_users`
- **Estudiantes**: puerto 5433, BD `dwsc_students`

### 2. Compilar los Proyectos

En cada directorio, ejecutar:

```bash
cd config-server
./mvnw clean package -DskipTests

cd ../manager-users
./mvnw clean package -DskipTests

cd ../manager-students
./mvnw clean package -DskipTests
```

### 3. Arrancar los Servicios

El orden es importante: primero el config-server, luego los microservicios.

**Config Server:**

```bash
cd config-server
./mvnw spring-boot:run
```

**Manager Users:**

```bash
cd manager-users
./mvnw spring-boot:run
```

**Manager Students:**

```bash
cd manager-students
./mvnw spring-boot:run
```

## Puertos y Endpoints

| Servicio         | Puerto | Endpoint              |
| ---------------- | ------ | --------------------- |
| Config Server    | 8888   | <http://localhost:8888> |
| Manager Users    | 8081   | <http://localhost:8081> |
| Manager Students | 8082   | <http://localhost:8082> |

### Swagger UI

- Users: <http://localhost:8081/swagger-ui/index.html>
- Students: <http://localhost:8082/swagger-ui/index.html>

## Credenciales

### Base de Datos

- Usuario: `estudiante`
- Contraseña: `estudiante`

## Servicios Disponibles

### Manager Users

- `GET /users` - Listar usuarios
- `GET /users/{id}` - Obtener usuario por ID
- `POST /users` - Crear usuario
- `PUT /users/{id}` - Actualizar usuario
- `DELETE /users/{id}` - Eliminar usuario

### Manager Students

- `GET /students` - Listar estudiantes
- `GET /students/{id}` - Obtener estudiante por ID
- `POST /students` - Crear estudiante
- `PUT /students/{id}` - Actualizar estudiante
- `DELETE /students/{id}` - Eliminar estudiante

- `GET /degrees` - Listar titulaciones
- `GET /degrees/{id}` - Obtener titulación por ID
- `POST /degrees` - Crear titulación

## Apagado

Para detener los servicios:

```bash
# Detener microservicios (Ctrl+C en cada terminal)

# Detener bases de datos
docker-compose down
```

