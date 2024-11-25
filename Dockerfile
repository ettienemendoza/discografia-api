#set1 

FROM gradle:jdk21 as builder

WORKDIR /app

COPY build.gradle settings.gradle ./

COPY src ./src

RUN gradle clean bootJar --no-daemon

#Set2

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
