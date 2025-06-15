# Stage 1: Build with Gradle + JDK 21
FROM gradle:8.14.2-jdk21 AS builder
WORKDIR /app

# Copy everything (let Gradle image own it)
COPY --chown=gradle:gradle . .

# Build the Spring Boot fat‑jar (skipping tests)
RUN gradle clean bootJar -x test --no-daemon

# Stage 2: Run on Java 21 JRE
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
