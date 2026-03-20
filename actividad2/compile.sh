#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/bellsoft-java8-amd64
export PATH=$JAVA_HOME/bin:$PATH

echo "=== Compilando proyecto con Java 8 ==="
mvn clean compile

echo ""
echo "=== Generando stubs CORBA ==="
idlj -fall -oldImplBase idl/NewsBuffer.idl

echo ""
echo "=== Copiando stubs a src ==="
mkdir -p src/main/java/NewsBufferApp
cp NewsBufferApp/*.java src/main/java/NewsBufferApp/

echo ""
echo "=== Recompilando con stubs ==="
mvn compile

echo ""
echo "=== Compilacion completada ==="
echo ""
echo "Para ejecutar:"
echo "  1. orbd -ORBInitialPort 1050"
echo "  2. export JAVA_HOME=/usr/lib/jvm/bellsoft-java8-amd64 && export PATH=\$JAVA_HOME/bin:\$PATH"
echo "  3. java -cp 'target/classes:\$JAVA_HOME/jre/lib/*' com.university.news.corba.NewsBufferServer -ORBInitialPort 1050"
echo "  4. mvn tomcat7:run"
