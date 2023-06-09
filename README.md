# Planes

Simple project with Scala, Akka, Cassandra, Kafka and Circe.

## Setup the environment

### Requirements

    export NEXUS_BASE_URL=https://$NEXUS_HOST_URL

### Code Style

Format the code:

    sbt scalafmt

### Artifacts

Generate the artifacts locally:

    sbt publishLocal

Generate the artifacts images publicly:

    sbt publish

### Docker images

Generate the docker images locally:

    sbt docker:publishLocal

Generate the docker images publicly:

    sbt docker:publish

### Launch the app

#### Using Docker 

Generate the docker images locally:

    sbt docker:publishLocal

Set the deployment version (ex: 0.1.0-SNAPSHOT):

    export VERSION=0.1.0-SNAPSHOT

Launch the services:

    docker-compose up

#### Using Minikube

Launch minikube

    minikube start

Launch the services

    cd ./minikube
    kubectl apply -f planes-secret.yaml
    kubectl apply -f planes-config.yaml
    kubectl apply -f cassandra.yaml
    kubectl apply -f planes-api.yaml

NOTES: Use cqlsh -u cassandra -p cassandra to run CQL queries

NOTES: Use minikube service planes-api-service --url to have the url

## Interacting with the sample

### Postman

You can test the endpoints by using the postman collection `api.postman_collection.json`. You can directly import it into Postman.

### Endpoints

Get all available vehicles:

    curl --location --request GET 'http://127.0.0.1:8080/api/planes'
