FROM openjdk:11-jdk-oracle

VOLUME /tmp

EXPOSE 8700

ARG JAR_FILE=target/gateway-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} gateway.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/gateway.jar"]

