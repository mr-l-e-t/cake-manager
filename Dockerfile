FROM amazoncorretto:21-alpine-jdk
LABEL authors="Lucas T"
COPY ./build/libs/cake-manager-0.0.1-SNAPSHOT.jar webapp.jar
EXPOSE 8080 8080
ENTRYPOINT ["java", "-jar", "webapp.jar"]
#ENTRYPOINT ["java", "-jar", "webapp.jar", "--spring.profiles.active=dev"]