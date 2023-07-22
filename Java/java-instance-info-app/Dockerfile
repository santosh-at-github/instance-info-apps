FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/webapp-example-1.0-SNAPSHOT.jar /app/webapp-example.jar

EXPOSE 8080

CMD ["java", "-jar", "webapp-example.jar"]
