#FROM maven:3.8.1-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY src ./src
#RUN mvn package -DskipTests
#
#FROM openjdk:17-alpine
#WORKDIR /app
#COPY --from=build /app/target/server.jar server.jar
#EXPOSE 8080
#CMD ["java", "-jar", "-Dspring.profiles.active=docker", "server.jar"]


FROM openjdk:17-alpine
COPY target/server.jar server.jar
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=docker", "server.jar"]