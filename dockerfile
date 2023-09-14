FROM maven:3.8.4-openjdk-20-slim as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src src

RUN mvn package -DskipTests

FROM openjdk:20-jdk-slim

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]