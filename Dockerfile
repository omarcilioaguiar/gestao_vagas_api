FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . /app

RUN apt-get install maven -y
RUN mvn clean installx

FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /target/gestao_vagas-0.0.1.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]