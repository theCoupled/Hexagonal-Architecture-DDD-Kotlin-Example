<h1 align="center">
Hexagonal Architecture, DDD & CQRS in Kotlin
</h1>

<p align="center">
Example of a Kotlin application using Domain-Driven Design (DDD) and Command Query Responsibility Segregation (CQRS) principles.
</p>

# Table of Contents
1. [How to run the application](#how-to-run-the-application)
    1. [Docker compose](#docker-compose)
    2. [Docker](#docker)
    3. [Gradle wrapper](#gradle-wrapper)
2. [The project](#the-project)
    4. [Project Architecture](#architecture)
    5. [Testing strategy](#testing-strategy)



How to run the application
-----------

There are three different ways to run the application:
- Docker compose
- Docker
- Gradle wrapper

The application server runs on the port 8080.

### Docker compose

This requires docker to be installed.

To run the application execute the following command from the project root directory:

`docker compose up --build`

Add `-d` at the end in case you want the application to run in the background.

To stop the application, run the following command from the project root directory:

`docker compose down`


To change the port, you will have to change it in the docker-compose.yml

### Docker
This requires docker to be installed

To create the image, run this command from the project root directory:

`docker build -t movie-recommender-app .`

To run the previously generated image, run the following command:

`docker run -p 8080:8080 movie-recommender-app`

Add `-d` in case you want the application to run in the background.

To change the port, you can do it in the `docker run` command.

### Gradle wrapper
This will use the gradlew wrapper that is already included within the project.

Run the following command from the project root directory:

`./gradlew bootRun`

To change the port, you can do in the `application.yml` file.


To run the tests:

`./gradlew test`


The project
-----------
### Domain
The project is a movie recommender application based on the user's movie taste.

### Architecture
The architecture applied in this project is the Hexagonal Architecture, also known as Ports & Adapters or Clean Architecture.

The reason for using this architecture is to aim for the long term maintenance by providing the following topics:
- A directory/package layout that defines the different responsibilities of the architecture layers.
- Defines the dependency rule between the architecture layers, by locating the domain as the core of the application, ending up with a loosely coupled domain from the other components.

<p align="center">

![Hexagonal architecture](/assets/clean-arch.png)
</p>

#### Packages
These are the packages of the architecture:
- application: this package answers the question of what does the service do?
- infrastructure: this package answers the question of what does the service use?
- api: this package answers the question of how can one use the service?
- domain: this package contains all the domain concepts and their invariance(business rules).

### Testing strategy
The testing strategy applied in this project is represented in the following diagram:
<p align="center">

![Testing diagram](/assets/test-strategy.png)
</p>

In the business logic testing, the "unit" of the unit tests is the whole use case, meaning that there is a suit of tests in the application layer covering all the business cases. The reasons for this approach and not for creating one suit of tests for every component are the following:
- As one of the keys of testing is refactoring, with this approach we test the behaviour rather than the implementation details.
- We can aim for 100% test coverage, and in case such coverage is not achieved, it will be because some test is missing or some business logic is not needed.