# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Cache dependencies and ensure Gradle wrapper files are present
COPY gradlew gradlew
# Explicitly copy wrapper files to avoid missing JAR in some build contexts
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/gradle-wrapper.jar
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/gradle-wrapper.properties

# Copy Gradle config for better layer caching
COPY settings.gradle settings.gradle
COPY build.gradle build.gradle

# Verify Gradle is runnable (this will also download the distribution per wrapper settings)
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
