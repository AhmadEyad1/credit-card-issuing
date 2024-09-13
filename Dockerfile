# Build
FROM eclipse-temurin:21.0.2_13-jdk-jammy as build

WORKDIR /usr/src/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests -T 1C

# Run
FROM gcr.io/distroless/java21-debian12

WORKDIR /app

COPY --from=build /usr/src/app/target/*.jar app.jar

USER nonroot:nonroot

CMD ["app.jar"]