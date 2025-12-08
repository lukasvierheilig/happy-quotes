FROM maven:3.9.11-eclipse-temurin-25 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM eclipse-temurin:25

ARG JAR_FILE=/app/target/*.jar
ARG APP_ENVIRONMENT=prod

ENV APP_ENV=$APP_ENVIRONMENT
ENV JAVA_TOOL_OPTIONS="-Xms128m -Xmx512m"

EXPOSE 8080

COPY --from=builder ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]