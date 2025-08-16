FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Final runtime image
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar hostel-finder.jar
ENTRYPOINT ["java", "-jar", "hostel-finder.jar"]