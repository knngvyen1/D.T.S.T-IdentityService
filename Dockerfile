FROM openjdk:11
FROM maven:3.6.3-jdk-11

EXPOSE 80

COPY . .
RUN $MAVEN_HOME/bin/mvn clean package

ENTRYPOINT ["java", "-Dspring.profiles.active=prod" ,"-jar","./target/usermanagement-0.0.1-SNAPSHOT.jar"]


