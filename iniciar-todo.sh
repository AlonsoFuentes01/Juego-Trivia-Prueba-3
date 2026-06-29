#!/bin/bash

echo "Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/eureka\" && ./mvnw spring-boot:run"'

echo "Esperando 15 segundos a que Eureka se estabilice..."
sleep 15

echo "Iniciando API Gateway..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/gateway\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Jugadores..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/jugadores\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Partidas..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/partidas\" && ./mvnw spring-boot:run"'

echo "Iniciando Microservicio Preguntas..."
osascript -e 'tell application "Terminal" to do script "cd \"'"$(pwd)"'/preguntas\" && ./mvnw spring-boot:run"'

echo "Ecosistema lanzado. Dashboard disponible en http://localhost:8761"