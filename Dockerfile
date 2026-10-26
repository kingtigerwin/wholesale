# syntax=docker/dockerfile:1
# Packages the jar already built by Gradle (./gradlew bootJar, or the CI build job); nothing is compiled here.
FROM eclipse-temurin:25-jre AS extract
WORKDIR /workspace
ARG JAR_FILE=build/libs/wholesale-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
# Split the jar into Spring Boot layers so unchanged dependencies stay cached image layers between releases.
RUN java -Djarmode=tools -jar app.jar extract --layers --launcher --destination extracted

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=extract /workspace/extracted/dependencies/ ./
COPY --from=extract /workspace/extracted/spring-boot-loader/ ./
COPY --from=extract /workspace/extracted/snapshot-dependencies/ ./
COPY --from=extract /workspace/extracted/application/ ./
EXPOSE 8080
# Datasource/Flyway settings come from SPRING_* environment variables set by the ECS task definition.
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
