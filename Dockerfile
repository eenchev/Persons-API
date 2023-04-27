FROM openjdk:21-slim-buster

COPY target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./app.jar"]