# spring-oauth2-microservices
An sample project with OAuth 2 and Microservices 

## Settings 
All settings are available in next files: 

**docker-compose.yml**, 

**Dockerfile** in modules, 

**application.yml** in modules  

## Database
Now is available database only for **Authorization** module. You should set up the tables, which are related to the **ClientEntity**.


## Run as microservice's cluster
Execute in terminal from root module

``
$ mvn clean install -DskipTests 
``

``
$ docker-compose up
``
