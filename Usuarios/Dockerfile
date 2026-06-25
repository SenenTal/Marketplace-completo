FROM eclipse-temurin:17-alpine

WORKDIR /app

COPY target/Usuarios-1.0-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]