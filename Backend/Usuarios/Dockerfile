FROM eclipse-temurin:17-jre

# Información del autor (opcional)
LABEL maintainer="Senen Castro"

# Carpeta de trabajo dentro del contenedor (Le dice a Docker: A partir de aquí trabajaré dentro de la carpeta "/app")
# Si no existe, Docker crea la carpeta /app automáticamente
WORKDIR /app

# Copiar el archivo .jar al contenedor (Le dice a Docker: Copia archivos desde tu computadora hacia el contenedor)
# Origen: "target/${JAR_FILE}", Destino: "/app/app.jar"
COPY target/Usuarios-1.0-SNAPSHOT.jar app.jar

# Puerto que utiliza la aplicación (Dice a Docker: "Esta aplicación escucha en el puerto 8003")
EXPOSE 8003

# Comando que se ejecutará al iniciar el contenedor (Si el usuario hace "docker run customer", Docker internamente "java -jar app.jar")
ENTRYPOINT ["java","-jar","app.jar"]