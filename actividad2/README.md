# News Manager - Productor/Consumidor CORBA

Sistema de gestión de noticias basado en el modelo Productor/Consumidor utilizando tecnología CORBA, XML y Servlets Java.

## Descripción

Esta aplicación implementa un buffer de noticias siguiendo el patrón Productor/Consumidor, donde:
- **Productores** insertan noticias en el buffer
- **Consumidores** leen o extraen noticias del buffer
- Las noticias se almacenan en formato XML y se validan contra un esquema XSD
- La comunicación entre componentes se realiza mediante CORBA

## Requisitos

- **Java 8** (JDK 1.8)
- **Maven 3.x**
- **ORBD** (Object Request Broker Daemon - incluido en Java 8)

## Estructura del Proyecto

```
news-manager/
├── pom.xml                     # Configuración Maven
├── compile.sh                  # Script de compilación
├── .gitignore                  # Archivos ignorados por Git
├── README.md                   # Este archivo
├── idl/
│   └── NewsBuffer.idl          # Definición de interfaz CORBA
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── NewsBufferApp/  # Stubs CORBA generados
│   │   │   └── com/university/news/
│   │   │       ├── model/Noticia.java
│   │   │       ├── xml/
│   │   │       │   ├── XMLCoder.java
│   │   │       │   ├── XMLDecoder.java
│   │   │       │   ├── Parser.java
│   │   │       │   └── SchemaValidator.java
│   │   │       ├── corba/
│   │   │       │   ├── NewsBufferImpl.java
│   │   │       │   ├── NewsBufferServer.java
│   │   │       │   └── NewsBufferClient.java
│   │   │       └── servlet/
│   │   │           ├── PutNewsServlet.java
│   │   │           ├── GetNewsServlet.java
│   │   │           └── ReadNewsServlet.java
│   │   ├── resources/
│   │   │   └── noticia.xsd    # Esquema XSD para validación
│   │   └── webapp/
│   │       ├── producer.html
│   │       ├── consumer.html
│   │       └── WEB-INF/web.xml
│   └── test/                   # Tests unitarios
│       └── java/
│           └── com/university/news/
│               ├── model/NoticiaTest.java
│               └── xml/
│                   ├── ParserTest.java
│                   ├── SchemaValidatorTest.java
│                   └── XMLCoderDecoderTest.java
```

## Modelo de Noticia

Cada noticia contiene los siguientes campos:

| Campo | Tipo | Validación |
|-------|------|------------|
| fecha | String | Formato `dd/mm/aaaa` |
| nivel | String | "alta", "media" o "baja" |
| descripcionCorta | String | 5-30 caracteres (sin espacios) |
| descripcionLarga | String | 20-250 caracteres (sin espacios) |
| etiquetas | List<String> | 1-6 hashtags (#palabra) |

## Compilación

### Paso 1: Configurar Java 8

```bash
export JAVA_HOME=/usr/lib/jvm/bellsoft-java8-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

### Paso 2: Compilar el proyecto

```bash
mvn clean compile
```

### Paso 3: Generar stubs CORBA

```bash
idlj -fall -oldImplBase idl/NewsBuffer.idl
```

### Paso 4: Copiar stubs generados

```bash
mkdir -p src/main/java/NewsBufferApp
cp NewsBufferApp/*.java src/main/java/NewsBufferApp/
```

### Paso 5: Recompilar

```bash
mvn compile
```

### O usar el script (Linux/Mac)

```bash
chmod +x compile.sh
./compile.sh
```

## Ejecución

La aplicación requiere **3 terminales** ejecutándose simultáneamente:

### Terminal 1: Iniciar ORB Daemon

```bash
orbd -ORBInitialPort 1050
```

### Terminal 2: Iniciar Servidor CORBA

```bash
export JAVA_HOME=/usr/lib/jvm/bellsoft-java8-amd64
export PATH=$JAVA_HOME/bin:$PATH
java -cp 'target/classes:$JAVA_HOME/jre/lib/*' com.university.news.corba.NewsBufferServer -ORBInitialPort 1050
```

Deberías ver:
```
Servidor NewsBuffer preparado y esperando...
Buffer configurado con maximo de 20 noticias.
```

### Terminal 3: Iniciar Tomcat

```bash
export JAVA_HOME=/usr/lib/jvm/bellsoft-java8-amd64
export PATH=$JAVA_HOME/bin:$PATH
mvn tomcat7:run
```

> **Importante:** El plugin tomcat7-maven-plugin usa su propio Java. Asegúrate de configurar JAVA_HOME antes de ejecutar `mvn`.

### Acceder a la aplicación

Abrir en el navegador:
- **Productor:** http://localhost:8080/news-manager/producer.html
- **Consumidor:** http://localhost:8080/news-manager/consumer.html

## Uso

### Insertar noticia (Productor)

1. Ir a `producer.html`
2. Rellenar el formulario con los datos de la noticia
3. Hacer clic en "Insertar Noticia"

**Datos de prueba:**
| Campo | Valor |
|-------|-------|
| Fecha | 20/03/2026 |
| Nivel | alta |
| Desc. Corta | FestivalMusicaAlmeria |
| Desc. Larga | Festival de musica en Almeria con artistas internacionales |
| Etiquetas | #musica #festivalAlmeria #ocio |

### Consumir noticia (Consumidor)

1. Ir a `consumer.html`
2. **READ:** Leer la primera noticia sin eliminarla del buffer
3. **GET:** Extraer y eliminar la primera noticia del buffer (FIFO)

## Ejecución de Tests

```bash
mvn test
```

**Resultados:** 35 tests (todos passing)

## Problemas Comunes

### Error: ClassNotFoundException: org.omg.CORBA.ORB
- **Causa:** Maven/Tomcat está usando Java 11+ (que no incluye CORBA)
- **Solución:** Configurar `JAVA_HOME` y `PATH` antes de ejecutar Maven:
  ```bash
  export JAVA_HOME=/usr/lib/jvm/bellsoft-java8-amd64
  export PATH=$JAVA_HOME/bin:$PATH
  mvn tomcat7:run
  ```

### Error: Could not find or load main class
- **Causa:** Nombre de clase o classpath incorrecto
- **Solución:** Verificar que la clase es `com.university.news.corba.NewsBufferServer` y el classpath incluye `$JAVA_HOME/jre/lib/*`

### Error: Connection refused
- **Causa:** ORB Daemon no está ejecutándose
- **Solución:** Ejecutar `orbd -ORBInitialPort 1050` antes que el servidor

## Interfaces CORBA

### IDL (NewsBuffer.idl)

```idl
module NewsBufferApp {
    interface NewsBuffer {
        boolean put(in string noticiaXML);
        boolean get(out string noticiaXML);
        boolean read(out string noticiaXML);
        boolean isEmpty();
        long getCount();
        oneway void shutdown();
    };
};
```

### Métodos

| Método | Descripción |
|--------|-------------|
| `put()` | Inserta una noticia XML en el buffer |
| `get()` | Extrae y elimina la primera noticia (FIFO) |
| `read()` | Lee la primera noticia sin eliminarla |
| `isEmpty()` | Verifica si el buffer está vacío |
| `getCount()` | Retorna el número de noticias en el buffer |
| `shutdown()` | Cierra el servidor CORBA |

## Tecnologías Utilizadas

- **Java 8** - Lenguaje de programación
- **CORBA** - Middleware para comunicación distribuida
- **XML** - Formato de intercambio de datos
- **XSD** - Validación de esquemas XML
- **Servlets** - Componentes web (Java EE)
- **Tomcat 7** - Servidor de aplicaciones
- **JUnit 4** - Framework de testing
- **Maven** - Herramienta de build

## Autor

- Desarrollado para la asignatura de Desarrollo Web basado en Servicios y Componentes
- Departamento de Informática - Universidad de Almería

## Licencia

Uso académico.
