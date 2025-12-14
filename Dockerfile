# We use Java 21, the current LTS version
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file produced by Maven
COPY target/*.jar app.jar

# Define the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the port
EXPOSE 8080