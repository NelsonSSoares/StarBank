RUN mkdir -p /app/files/uploads /app/files/downloads
WORKDIR /app
COPY target/userapi-0.0.1-SNAPSHOT.jar userapi.jar
ENTRYPOINT ["java", "-jar", "userapi.jar"]

#
#FROM eclipse-temurin:21-jdk
#COPY target/*.jar userapi.jar
#ENTRYPOINT ["java", "-jar", "userapi.jar"]
