# Use a Java 21 image
FROM openjdk:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file (produced by Maven build)
COPY target/*.jar app.jar

# Define the entry point to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the internal port
EXPOSE 8080