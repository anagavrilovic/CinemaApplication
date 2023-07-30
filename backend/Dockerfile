# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project and build
COPY . .
RUN mvn package -DskipTests

# Run stage
FROM openjdk:17-jdk
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/cinema-0.0.1-SNAPSHOT.jar .

# Expose the port on which the application listens
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "cinema-0.0.1-SNAPSHOT.jar"]
