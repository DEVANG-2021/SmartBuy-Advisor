FROM openjdk:11
RUN ./mvnw clean package
WORKDIR /app
COPY target/Project-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
