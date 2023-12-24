FROM openjdk:17-alpine
COPY target/server.jar server.jar
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=${SPRING_PROFILE}", "server.jar"]
