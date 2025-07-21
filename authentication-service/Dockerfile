FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/authentication-service.jar authentication-service.jar
ENTRYPOINT ["java", "-jar", "/authentication-service.jar"]