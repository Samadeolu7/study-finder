# ┌────────────────────────────────────────────┐
# │ Stage 1: Build with your Gradle wrapper   │
# └────────────────────────────────────────────┘
FROM eclipse-temurin:17-jdk AS builder

# 1. Create app directory
WORKDIR /app

# 2. Copy only the wrapper first (speeds up caching)
COPY gradlew .
COPY gradle ./gradle

# 3. Copy the build scripts
COPY build.gradle settings.gradle ./

# 4. Ensure the wrapper is executable
RUN chmod +x ./gradlew

# 5. Download dependencies (this layer caches them)
RUN ./gradlew --no-daemon dependencies

# 6. Copy the rest of the source code
COPY src ./src

# 7. Build the fat‑jar
RUN ./gradlew clean build -x test --no-daemon


# ┌────────────────────────────────────────────┐
# │ Stage 2: Run the fat‑jar on a slim JRE     │
# └────────────────────────────────────────────┘
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/build/libs/*.jar ./app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run!
ENTRYPOINT ["java","-jar","app.jar"]
