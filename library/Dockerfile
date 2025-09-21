# Sử dụng JDK 17 nhẹ (slim) làm base image
FROM openjdk:17-jdk-slim

# Copy file jar vào container, đổi tên thành app.jar
COPY target/library-0.0.1-SNAPSHOT.jar app.jar

# Lệnh chạy khi container start
ENTRYPOINT ["java","-jar","/app.jar"]

# Mở cổng 8080 (Spring Boot mặc định)
EXPOSE 8080
