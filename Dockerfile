FROM gradle:8.6.0 AS build
COPY --chown=gradle:gradle . /app
WORKDIR /app
RUN gradle build --no-daemon

FROM gcr.io/distroless/java21-debian12:latest

EXPOSE 3010

COPY --from=build /app/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar","/app.jar"]