FROM adoptopenjdk/openjdk11:latest
WORKDIR /app
EXPOSE 8080
ADD /build/libs/carefinder-api-0.0.1-SNAPSHOT.jar ./server.jar
CMD ["java", "-jar", "server.jar"]