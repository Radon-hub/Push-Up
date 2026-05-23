# =====================================================
# Stage 1: Build
# =====================================================
FROM gradle:9.2.1-jdk21-jammy AS build

LABEL maintainer="Radon"
LABEL app="PushUp"
LABEL purpose="Event Fetcher System"

WORKDIR /home/gradle/src

# Copy only build files first for caching
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle

# Download dependencies (cached layer)
RUN --mount=type=cache,target=/home/gradle/.gradle gradle dependencies --no-daemon || true

# Copy source code
COPY src src

# Build application
RUN --mount=type=cache,target=/home/gradle/.gradle gradle bootJar --no-daemon -x test --stacktrace --info

# =====================================================
# Stage 2: Runtime
# =====================================================
FROM eclipse-temurin:21-jre-jammy

ENV APP_HOME=/app

# Create non-root user (Ubuntu/Jammy syntax)
RUN groupadd -g 1000 pushup && \
    useradd -u 1000 -g pushup \
    -d ${APP_HOME} \
    -m \
    -s /bin/bash \
    pushupuser

WORKDIR ${APP_HOME}

# Copy JAR
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Fix ownership
RUN chown -R pushupuser:pushup ${APP_HOME}

USER pushupuser

EXPOSE 8080

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]