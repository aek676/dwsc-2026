#!/bin/bash

BASE_DIR="/home/anass/workspaces/dwsc-2026/actividad7"

cd "$BASE_DIR/eureka-server"
./mvnw clean package -DskipTests
./mvnw spring-boot:run &
echo "Eureka Server iniciado (PID: $!)"
sleep 30

cd "$BASE_DIR/eureka-client-subject"
./mvnw clean package -DskipTests
./mvnw spring-boot:run &
echo "Subject iniciado (PID: $!)"

cd "$BASE_DIR/eureka-client-verb"
./mvnw clean package -DskipTests
./mvnw spring-boot:run &
echo "Verb (default) iniciado (PID: $!)"

cd "$BASE_DIR/eureka-client-verb"
./mvnw clean package -DskipTests
./mvnw spring-boot:run -Dspring-boot.run.profiles=positive-verb &
echo "Verb (positive-verb) iniciado (PID: $!)"

cd "$BASE_DIR/eureka-client-verb"
./mvnw clean package -DskipTests
./mvnw spring-boot:run -Dspring-boot.run.profiles=negative-verb &
echo "Verb (negative-verb) iniciado (PID: $!)"

cd "$BASE_DIR/eureka-client-compl"
./mvnw clean package -DskipTests
./mvnw spring-boot:run &
echo "Compl iniciado (PID: $!)"

cd "$BASE_DIR/eureka-client-sentence"
./mvnw clean package -DskipTests
./mvnw spring-boot:run &
echo "Sentence iniciado (PID: $!)"

cd "$BASE_DIR/eureka-client-sentence-feign"
./mvnw clean package -DskipTests
./mvnw spring-boot:run &
echo "Sentence-Feign iniciado (PID: $!)"

cd "$BASE_DIR/gateway"
./mvnw clean package -DskipTests
./mvnw spring-boot:run &
echo "Gateway iniciado (PID: $!)"

echo ""
echo "=== Todos los servicios iniciados ==="
echo "Usa 'ps' para ver los procesos"
echo "Usa './stop-all-services.sh' para detener todos"