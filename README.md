[![Build Status](https://travis-ci.com/harmony1358/treeset.svg?branch=master)](https://travis-ci.com/harmony1358/treeset) [![Coverage Status](https://coveralls.io/repos/github/harmony1358/treeset/badge.svg?service=github)](https://coveralls.io/github/harmony1358/treeset)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/d555e12a786e49b691ab2790ad28c5c9)](https://www.codacy.com/app/harmony1358/treeset?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=harmony1358/treeset&amp;utm_campaign=Badge_Grade)

# TreeSet API

Example TreeSet backend for bit4mation.  
Built upon SpringBoot with embedded HSQL in-memory database.  
Uses JPA/Hibernate as a data layer.  
  
## Concept

Project implements unidirectional tree structure basing on "parent" reference.  
The concept assumes that tree branches will be lazily loaded by frontend to avoid deep fetch exposure.  
Root nodes have "null" parent reference.  

## Classes
[TreeNode.java](https://github.com/harmony1358/treeset/blob/master/src/main/java/pl/bit4mation/treeset/entities/TreeNode.java)   
Main Data entity - responsible for storing node information, such as number and parent relation  

[TreeNodeRepository.java](https://github.com/harmony1358/treeset/blob/master/src/main/java/pl/bit4mation/treeset/dao/TreeNodeRepository.java)  
Spring CRUD repository template providing basing fetching and updating functionality  
  
[TreeController.java](https://github.com/harmony1358/treeset/blob/master/src/main/java/pl/bit4mation/treeset/controllers/TreeController.java)  
MVC Controller exposing REST API with CRUD operations  
  
[SwaggerConfig.java](https://github.com/harmony1358/treeset/blob/master/src/main/java/pl/bit4mation/treeset/config/SwaggerConfig.java)  
Configuration bean for Swagger  
  
[Application.java](https://github.com/harmony1358/treeset/blob/master/src/main/java/pl/bit4mation/treeset/Application.java)  
SpringBoot Application launcher  
  
## API  
  
REST API ref is held and managed by Swagger.  Please go to [Running](#running) section for more info.

## Building
  
Project is built by "gradle" build system:  

`./gradlew build`

## Build Docker Image
  
You can build docker image with gradle task:  

`./gradlew distDocker`

## Testing
  
Project uses JUnit for testing and JaCoCo for coverage reporting. 
Coverage reports are pushed to [Coveralls.io](https://coveralls.io/) when built on travis-ci/github  
Running test gradle task:  
  
`./gradlew test`
  
## CI
  
Project uses [Travis-CI](https://travis-ci.org/) for Continuous Integration and deployment.  
Builds are triggered automatically after each commit.  
CI pipeline configuration can be found here:  [.travis.yml](https://github.com/harmony1358/treeset/blob/master/.travis.yml)  
One-click deployment was tested on Heroku.  

## Automatic code reviews and linting  
  
Project performs automatic code reviews with [Codacy](https://app.codacy.com).  
Code reviews are performed automatically during CI pipeline. 
  
## Running
  
Project can be launched locally with gradle task:    
  
`./gradlew bootRun`  
  
On production environment - docker image and binary launcher is used.  
After launching locally you should be able to access swagger UI here:  
  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  

## Frontend  
  
This project has companion project with frontend WebApp.  
Please read here: [README.md](https://github.com/harmony1358/treesetclient/blob/master/README.md) to find how to build and launch both projects together.
