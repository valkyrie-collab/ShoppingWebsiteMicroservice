FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/order-service.jar order-service.jar
ENTRYPOINT ["java", "-jar", "/order-service.jar"]