FROM openjdk:11-jdk-oracle

VOLUME /tmp

EXPOSE 8702

ARG JAR_FILE=target/authorization-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} authorization.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/authorization.jar"]