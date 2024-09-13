# Credit Card Issuing System

## Tech Stack
- **Spring Boot v3.2.9**: Framework for building the application.
- **Java 21**: Programming language used.
- **H2 Database**: In-memory database used for runtime.
- **Flyway**: Database migration tool.
- **Mockito**: Framework for unit testing.
- **OpenAPI**: Documentation of RESTful APIs.
- **Docker**: Containerization for easy deployment.
- **S3**: Amazon Simple Storage Service for object storage.

## Project Structure

The project follows a clean architecture with separate layers for:
- **Application**: Contains services and business logic.
- **Domain**: Defines the enterprise business and domain logic.
- **Infrastructure**: Manages data access, external services, and configurations.

## Running the Application
You can run the application in several ways:

### Local Execution
Run the JAR file directly:
```shell
java -jar target/credit-card-issuing-1.0.0.jar
```

### Using Docker
Build the Docker image and run it:
```shell
# Build Docker image
docker build -t credit-card-issuing:latest .

# Run Docker container
docker run -d -p 8080:8080 credit-card-issuing:latest
```

### Testing
Run unit tests using Maven:
```text
mvn test
```

### Makefile Commands
You can also use the provided Makefile for common tasks:

- Build the Project
```text
make build
```
- Run the Application
```text
make run
```
- Run Tests
```text
make test
```
- Clean the Project
```text
make clean
```
- Build Docker Image
```text
make docker-build
```
- Run Docker Image
```text
make docker-run
```
- Clean Docker Image
```text
make docker-clean
```

## API Documentation
The application provides OpenAPI documentation available at /swagger-ui.html once the application is running. Additionally, the OpenAPI specifications are available in JSON format through the /v3/api-docs endpoint

## Database
The application uses H2 Database as an in-memory database for development and testing purposes. H2 is lightweight, fast, and provides an easy-to-use web console for querying the database.

