FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/stock-receiver-0.0.1-SNAPSHOT.jar
ARG PROPERTIES_FILE=src/main/resources/application.yml
COPY ${JAR_FILE} app.jar
COPY ${PROPERTIES_FILE} application.yml
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080