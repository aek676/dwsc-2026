#!/bin/bash

echo "=== Buscando y detieneiendo servicios de Spring Boot ==="

pids=$(ps aux | grep -E 'actividad7/eureka' | grep -v grep | awk '{print $2}')

if [ -z "$pids" ]; then
  echo "No se encontraron servicios en ejecucion"
  exit 0
fi

echo "PIDs encontrados: $pids"
echo "Deteniendo procesos..."

kill -15 $pids

sleep 2

pids_left=$(ps aux | grep -E 'actividad7/eureka' | grep -v grep | awk '{print $2}')

if [ -n "$pids_left" ]; then
  echo "Intentando con senal forzada -9..."
  kill -9 $pids_left
fi

echo "Verificando que todos los servicios estan detenidos..."

sleep 1

remaining=$(ps aux | grep -E 'actividad7/eureka' | grep -v grep | wc -l)

if [ "$remaining" -eq 0 ]; then
  echo "Todos los servicios han sido detenidos"
else
  echo "ADVERTENCIA: $remaining procesos todavia corriendo"
  ps aux | grep -E 'actividad7/eureka' | grep -v grep
fi

