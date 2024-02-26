FROM openjdk:21-slim
COPY target/*.jar courier-service.jar
ENTRYPOINT ["java","-jar","courier-service.jar"]