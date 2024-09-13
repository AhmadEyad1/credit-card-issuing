PROJECT_NAME := credit-card-issuing
PROJECT_VERSION := 1.0.0
JAR_FILE := target/$(PROJECT_NAME)-${PROJECT_VERSION}.jar
DOCKER_IMAGE := $(PROJECT_NAME):$(PROJECT_VERSION)

.DEFAULT_GOAL := help

MVN := ./mvnw
MVN_BUILD := $(MVN) clean package -DskipTests
MVN_TEST := $(MVN) test
MVN_CLEAN := $(MVN) clean

DOCKER_BUILD := docker build -t $(DOCKER_IMAGE) .
DOCKER_RUN := docker container run -d -p 8080:8080 $(DOCKER_IMAGE)
DOCKER_CLEAN := docker rmi $(DOCKER_IMAGE)

.PHONY: help build run test clean docker-build docker-run docker-clean

help:
	@echo "Available targets:"
	@echo "  build         Build the project and create the jar file"
	@echo "  run           Run the Spring Boot application"
	@echo "  test          Run unit tests"
	@echo "  clean         Clean the project"
	@echo "  docker-build  Build a Docker image"
	@echo "  docker-run    Run the Docker container"
	@echo "  docker-clean  Remove the Docker image"

## Build the project
build: $(JAR_FILE)

$(JAR_FILE):
	@$(MVN_BUILD)

## Run the application
run: $(JAR_FILE)
	java -jar $(JAR_FILE)

## Run tests
test:
	@$(MVN_TEST)

## Clean the project
clean:
	@$(MVN_CLEAN)

## Build Docker image
docker-build:
	$(DOCKER_BUILD)

## Run Docker container
docker-run:
	$(DOCKER_RUN)

## Clean the Docker image
docker-clean:
	$(DOCKER_CLEAN)