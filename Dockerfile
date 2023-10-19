FROM openjdk:18-ea-slim
COPY --chown=devops-api.jar devops-api.jar ./
ADD target/*.jar devops-api.jar
ENTRYPOINT [ "java", "-jar", "devops-api.jar" ]