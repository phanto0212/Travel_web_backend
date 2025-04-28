# Sử dụng image base Java (OpenJDK)
FROM openjdk:17-jdk-slim

# Thêm thông tin về ứng dụng
ARG JAR_FILE=target/*.jar

# Sao chép file JAR vào container
COPY ${JAR_FILE} app.jar

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Mở port 8080 để ứng dụng có thể truy cập
EXPOSE 8080