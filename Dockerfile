FROM openjdk:17

RUN mkdir /app

COPY build/libs/hanghae-plus-0.0.1-SNAPSHOT.jar  /app/app.jar

WORKDIR /app


CMD ["java", "-jar", "app.jar"]