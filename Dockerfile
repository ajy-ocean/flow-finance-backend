# =========================================================================
# Stage 1: The Build Stage (Uses Maven to compile the JAR)
# =========================================================================
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set the working directory for Maven
WORKDIR /app

# Copy the Maven project files first to leverage Docker caching
COPY pom.xml .
COPY src /app/src

# Package the application (skipping tests for faster deployment)
# This command CREATES the target/ directory
RUN mvn clean package -DskipTests

# =========================================================================
# Stage 2: The Runtime Stage (A lightweight environment to run the JAR)
# =========================================================================
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR artifact from the 'build' stage
# The 'target' folder now exists in the 'build' stage's filesystem.
COPY --from=build /app/target/*.jar app.jar

# Define the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]

# Expose the internal port
EXPOSE 8080