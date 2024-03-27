FROM openjdk:21-jdk-bullseye AS builder

WORKDIR /app

COPY src src
COPY .mvn .mvn
COPY pom.xml .
COPY mvnw.cmd .
COPY mvnw .
COPY movies.json .

RUN chmod a+x /app/mvnw
RUN ./mvnw package -Dmaven.test.skip=true

FROM openjdk:21-jdk-bullseye

WORKDIR /app_run

COPY --from=builder /app/target/ibf-b4-ssf-assessment-0.0.1-SNAPSHOT.jar movies.jar
COPY --from=builder /app/movies.json .

ENV PORT=8080

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar movies.jar