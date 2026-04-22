# Build stage
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/gram-panchayat-platform-1.0.0.war app.war

# Create uploads directory
RUN mkdir -p /app/uploads/qrcodes /app/uploads/certificates /app/uploads/documents

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.war"]
