# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Cache dependencies
COPY gradlew gradlew
COPY gradle gradle
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle
RUN chmod +x gradlew && ./gradlew --version

# Copy source and build (skip tests for faster image build)
COPY src src
RUN ./gradlew clean bootJar -x test

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Optional JVM flags can be passed via JAVA_OPTS
ENV JAVA_OPTS=""

# Copy the Boot JAR from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

# Use sh -c to allow JAVA_OPTS expansion
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
