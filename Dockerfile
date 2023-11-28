FROM openjdk:11
VOLUME /tmp
EXPOSE 5432
ARG JAR_FILE=build/libs/MojRacunApiService-1.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

LABEL name="moj-racun" version="1.0"