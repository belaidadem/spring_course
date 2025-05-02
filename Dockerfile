FROM openjdk:11-jdk-slim

WORKDIR /app

# Copy the specific JAR file from the current directory
COPY app.jar app.jar

# Run the application with explicit main class (adjust if needed)
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use port 8081 instead of 8080
EXPOSE 8080