FROM maven:3.8.5-openjdk-17  as build

WORKDIR /application
USER root
COPY . .

RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:17-alpine
WORKDIR /application
# TODO use non root user for run app
COPY --from=build /application/target/*.jar application.jar
ENTRYPOINT java -Xmx512m -jar application.jar