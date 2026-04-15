#!/bin/bash
set -e

ORB_HOST=${ORB_HOST:-localhost}
ORB_PORT=${ORB_PORT:-1050}

echo "Esperando a que ORB esté disponible en $ORB_HOST:$ORB_PORT..."
until nc -z "$ORB_HOST" "$ORB_PORT"; do
	echo "Esperando ORB..."
	sleep 2
done

echo "ORB disponible. Iniciando servidor CORBA..."
java -cp "target/classes:$JAVA_HOME/jre/lib/*" \
	com.university.news.corba.NewsBufferServer \
	-ORBInitialPort "$ORB_PORT" -ORBInitialHost "$ORB_HOST"

while true; do
	sleep 1
done
