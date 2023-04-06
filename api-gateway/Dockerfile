# base image
FROM openjdk:17

# coping jar file, calling jar file as app.jar
COPY target/*.jar app.jar

# entrypoint command
ENTRYPOINT ["java", "-jar", "/app.jar"]