# Actividad 3 - Proyecto Maven WAR

## Inicialización del Proyecto

### Paso 1: Crear proyecto Maven con archetype webapp

```bash
mvn archetype:generate -DgroupId=es.ual -DartifactId=actividad3 -DarchetypeArtifactId=maven-archetype-webapp -Dpackaging=war -DinteractiveMode=false
```

### Paso 2: Estructura generada

- **Group ID:** es.ual
- **Artifact ID:** actividad3
- **Packaging:** war

### Paso 3: Estructura de directorios

```
actividad3/
├── pom.xml
└── src/
    └── main/
        ├── resources/
        └── webapp/
            ├── index.jsp
            └── WEB-INF/
                └── web.xml
```
