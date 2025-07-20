FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/service-registry.jar service-registry.jar
ENTRYPOINT ["java", "-jar", "/service-registry.jar"]