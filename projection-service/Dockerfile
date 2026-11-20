FROM gradle:9.6.1-jdk17-corretto AS build

WORKDIR /workspace

COPY settings.gradle ./
COPY ingestion-publisher/build.gradle ingestion-publisher/build.gradle
COPY projection-service/build.gradle projection-service/build.gradle

RUN gradle :projection-service:dependencies --no-daemon

COPY projection-service/src projection-service/src

RUN gradle :projection-service:bootJar --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /workspace/projection-service/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
