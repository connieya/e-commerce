FROM openjdk:17

WORKDIR /app

COPY build.gradle /app

RUN ./gradlew build

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} /app/app.jar


CMD ["java", "-jar", "app.jar"]