# syntax=docker/dockerfile:experimental
FROM openjdk:11 AS build
WORKDIR /workspace/app

COPY . /workspace/app
#RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
RUN ./gradlew clean build

RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM openjdk:11
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build /workspace/app/scripts/start.sh /app
RUN chmod +x /app/start.sh
ENTRYPOINT ["sh", "-c", "/app/start.sh"]