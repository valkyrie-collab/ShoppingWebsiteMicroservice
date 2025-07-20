FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/product-service.jar product-service.jar
ENTRYPOINT ["java", "-jar", "/product-service.jar"]