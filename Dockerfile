# Use a lightweight JDK base image
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file into the container
COPY ./target/balihome-0.0.1-SNAPSHOT.jar app.jar

# Expose port your Spring Boot app runs on
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
