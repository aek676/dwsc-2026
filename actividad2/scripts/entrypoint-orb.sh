#!/bin/bash
set -e

echo "Iniciando ORB Daemon en puerto 1050..."
orbd -ORBInitialPort 1050 -ORBInitialHost 0.0.0.0

while true; do
	sleep 1
done
