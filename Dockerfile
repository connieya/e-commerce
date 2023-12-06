FROM openjdk:17

RUN mkdir /app

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /app/app.jar

WORKDIR /app


CMD ["java", "-jar", "app.jar"]