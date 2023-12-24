# What is this about? 
This is a **URL-Shortener** project concept  

### Original Load Requirements:
- 100M users/year
- Each user shortens 10 URLs/year
- Each URL is being used 10 times/day
- Expected RPS (insert): 31.71 
- Expected RPS (access): 115,741


## Parts
- Spring Boot WebFlux
- Cassandra
- Redis
- MongoDB
- Gatling Load Test


# I. How to run? (Intellij IDEA)
### 1. Import as Maven project

### 2. Run `docker-compose-`<database_name>`-dev.yml`
**Command**: `docker-compose -f ./docker-compose-cassandra-dev.yml up -d`

*NOTE: At the moment, only Cassandra is fully tested*  

### 3. Choose Maven profile (database)
#### It will:
- **apply** the underlying **Maven module**
- apply **Spring profile** to application.yml
- hit `mvn install`

### 4. Run with provided Intellij run configurations
- URL Shortener - Server
- URL Shortener - Gatling - WarmUp
- URL Shortener - Gatling - Load Test

### 5. Postman
`.postman` directory contains a ready-to-use collection.

*Note: you may need to replace `{{host_url}}` with `localhost:8080` or configure as a variable at Postman Environment.*

# II. How to run dockerized Load Test + WebFlux Server + Cassandra Cluster 
1. Make sure you stop local Intellij Server instance
2. Run command: `docker-compose -f .\docker-compose-cassandra-load-test.yml up --build -d`
3. Results will be uploaded to `gatling-results` folder (shared docker volume)


# III. Add your feedback
Suggest improvements, bugs, gaps

