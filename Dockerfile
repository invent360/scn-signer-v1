FROM gradle:4.7.0-jdk8-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/scn-signer-v1
WORKDIR /home/gradle/scn-signer-v1
RUN gradle build --no-daemon
RUN ls -ltr /home/gradle/scn-signer-v1

FROM openjdk:8-jre-slim
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/scn-signer-v1/lib/build/libs/*.jar /app.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
