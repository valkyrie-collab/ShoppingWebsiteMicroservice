FROM openjdk:26-oraclelinux9
LABEL authors="valkyrie"
ADD target/cart-service.jar cart-service.jar
ENTRYPOINT ["java", "-jar", "/cart-service.jar"]