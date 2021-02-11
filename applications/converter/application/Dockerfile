FROM openjdk:11.0.4-jre
COPY build/libs/*.jar /converter.jar
ENTRYPOINT ["java","-jar","/converter.jar"]