FROM maven AS builder

COPY ./src /tmp/src
COPY ./pom.xml /tmp

WORKDIR /tmp
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-alpine
WORKDIR /app

COPY --from=builder /tmp/target/Elfin*.jar /app/Elfin.jar
COPY --from=builder /tmp/src/main/resources/bpmn /app/bpmn

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "/app/Elfin.jar"]