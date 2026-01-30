# Use Maven + JDK to build and run in one image
FROM maven:3.9.1-eclipse-temurin-17

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the Spring Boot jar
RUN mvn clean package -DskipTests

# Expose the port your app uses
EXPOSE 8081

# Run the jar
CMD ["java", "-jar", "target/Usermanagement-0.0.1-SNAPSHOT.jar"]
