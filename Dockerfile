# Stage 1: Build with your Gradle wrapper
FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

# 1. Copy the wrapper and make it executable
COPY gradlew .
COPY gradle ./gradle
RUN chmod +x gradlew

# 2. Copy build scripts
COPY build.gradle settings.gradle ./

# 3. Copy source
COPY src ./src

# 4. Build the fat‑jar (downloads deps here)
RUN ./gradlew clean build -x test --no-daemon

# Stage 2: Run on a slim JRE
FROM eclipse-temurin:17-jre

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
