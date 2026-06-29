@echo off

echo Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)...
cd eureka
start cmd /k "mvnw spring-boot:run"

echo Esperando 15 segundos a que Eureka se estabilice...
timeout /t 15 /nobreak > null

echo Iniciando API Gateway...
cd ../gateway
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Jugadores...
cd ../jugadores
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Partidas...
cd ../partidas
start cmd /k "mvnw spring-boot:run"

echo Iniciando Microservicio Preguntas...
cd ../preguntas
start cmd /k "mvnw spring-boot:run"

echo Ecosistema lanzado. Dashboard disponible en http://localhost:8761