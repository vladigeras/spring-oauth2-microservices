FROM openjdk:11-jdk-oracle

VOLUME /tmp

EXPOSE 8701

ARG JAR_FILE=target/discovery-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} discovery.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/discovery.jar"]

