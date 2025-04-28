# Base image chỉ cần JDK để chạy
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy file JAR đã build sẵn vào container
COPY target/tourist-0.0.1-SNAPSHOT.jar app.jar

# Chạy file JAR
ENTRYPOINT ["java", "-jar", "app.jar"]

# Mở cổng 8080
EXPOSE 8080