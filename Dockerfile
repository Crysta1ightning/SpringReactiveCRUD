FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/product-backend.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/app.jar"]