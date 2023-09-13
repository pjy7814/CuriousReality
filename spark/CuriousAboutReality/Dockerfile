FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} curiousreality.jar
ENTRYPOINT ["java", "-jar", "curiousreality.jar"]
