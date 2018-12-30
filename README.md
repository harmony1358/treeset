[![Build Status](https://travis-ci.com/harmony1358/treeset.svg?branch=master)](https://travis-ci.com/harmony1358/treeset) [![Coverage Status](https://coveralls.io/repos/github/harmony1358/treeset/badge.svg)](https://coveralls.io/github/harmony1358/treeset)

# TreeSet API

Example TreeSet backend for bit4mation.
Built upon SpringBoot with embedded HSQL in-memory database. Uses JPA/Hibernate as a data layer.
Project builds with gradle build system and uses Travis-CI for Continuous Integration.
Tests are performed by JUnit with JaCoCo reports that are furher pushed to Coveralls.io from Travis.
This project is ready to deploy on any env with one-click or/and by automated CD pipeline.
Deployment was tested on Heroku.

#### Building

`gradle build`

#### Build Docker Image

`gradle distDocker`

#### Testing

`gradle test`

#### Running

`gradle bootRun`

#### Swagger
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


