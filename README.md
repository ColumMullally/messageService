# Messaginge Service

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.


### Installing

1. Type mvn clean install to run the test and retrieve required Maven dependencies and build jar.
```
mvn clean install 
```

## Running the tests

Type the following in the console to run the unit tests:
```
mvn test
```
## Running

Type the following in the console to run application after your have finished the installation guide (Requires Docker):
```
docker-compose up
```

This will spin up mongoDB and the application which will be accessible on localhost:9091
I would recommend using the postman collection in the repo 
note: did not have time to automate env variable with the collection,
so some manual editing of the ids may be necessary.

##Future Plans

###Scale

1. cluster/ share db to make infrastructure more scale able.
2. setup cacheing on get by id methods for user and get by participants in conversations to reduce look up latency.
3. introduce pagination to reduce the size of data transfer. 
4. Current model to get new message is to poll get message by conversationID this would put the service under unnecessary stress:
   would update using a separate lambda function to act as watcher against db and adopt more of a pub/sub model potentially using web hooks.
   
###Clean up
1. perform better Unit testing that actually testing individual units of code.
2. handle exceptions in a meaningful way for the consumer instead of 500 errors
3. create a facade server that takes some of the business logic out of the controllers