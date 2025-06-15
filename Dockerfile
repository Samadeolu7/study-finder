# ┌────────────────────────────────────────────┐
# │ Stage 1: Build with the official Gradle   │
# └────────────────────────────────────────────┘
FROM gradle:8.14.2-jdk17 AS builder

# Set working dir
WORKDIR /app

# Copy everything and let the Gradle image own it
COPY --chown=gradle:gradle . .

# Build the fat‑jar (skipping tests)
RUN gradle clean bootJar -x test --no-daemon

# ┌────────────────────────────────────────────┐
# │ Stage 2: Run on a slim JRE                 │
# └────────────────────────────────────────────┘
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the fat‑jar produced by the 'bootJar' task
COPY --from=builder /app/build/libs/*.jar ./app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
