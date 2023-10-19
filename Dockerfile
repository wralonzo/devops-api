FROM openjdk:18-ea-slim
ADD target/*.jar devops-api.jar
ENTRYPOINT [ "java", "-jar", "devops-api.jar" ]