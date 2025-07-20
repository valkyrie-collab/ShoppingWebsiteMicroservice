FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/api-gateway.jar api-gateway.jar
ENTRYPOINT ["java", "-jar", "/api-gateway.jar"]