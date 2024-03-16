#
# Globals
#
IMAGE_NAME:=sip-backend-seed
CONTAINER_NAME:=sip-backend-seed
TAG:=latest
CURRENT_DIR=$(shell pwd)

.PHONY: clean
clean:
	./gradlew clean

##
## Local Gradle
##
.PHONY: build
build:
  # Build local and run unit tests
	./gradlew build

.PHONY: run
run: run-dependencies
	# run the app
	./gradlew bootRun --args='--spring.profiles.active=dev'

.PHONY: docker-build
docker-build:
  # Build local and run unit tests
	docker build -t ${IMAGE_NAME}:${TAG} .
##
## Local Docker
##
.PHONY: run-dependencies
run-dependencies:
	# start dependent services in background
	docker-compose up -d --no-recreate postgres

.PHONY: run-docker
run-docker: docker-build
	docker-compose up -d

.PHONY: down
down:
	docker-compose down
