FROM openjdk:18-ea-slim
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} devops-api.jar
ENTRYPOINT [ "java", "-jar", "/devops-api.jar" ]