FROM eclipse-temurin:8-jdk AS base

RUN apt-get update && apt-get install -y --no-install-recommends \
  maven \
  netcat-openbsd \
  && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY idl ./idl

RUN idlj -fall -oldImplBase idl/NewsBuffer.idl && \
  mkdir -p src/main/java/NewsBufferApp && \
  cp NewsBufferApp/*.java src/main/java/NewsBufferApp/ && \
  mvn clean package -DskipTests

EXPOSE 1050

CMD ["tail", "-f", "/dev/null"]

FROM tomcat:9-jre8 AS tomcat
EXPOSE 8080

COPY --from=base /app/target/news-manager-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/news-manager.war
