FROM gradle:9.6.1-jdk17-corretto AS build

WORKDIR /workspace

COPY settings.gradle ./
COPY ingestion-publisher/build.gradle ingestion-publisher/build.gradle
COPY projection-service/build.gradle projection-service/build.gradle

RUN gradle :ingestion-publisher:dependencies --no-daemon

COPY ingestion-publisher/src ingestion-publisher/src

RUN gradle :ingestion-publisher:bootJar --no-daemon

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /workspace/ingestion-publisher/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
