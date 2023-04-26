FROM openjdk:15-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ARG TYPE
ENV TYPE=$TYPE
ENTRYPOINT ["java","-Dspring.profiles.active=$TYPE","-jar","app.jar"]