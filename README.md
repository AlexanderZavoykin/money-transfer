# Money Transfer
Simple money transfer service implementation. 
Provides RESTful API for account management (create, close, update limit) 
and money transactions (withdraw, deposit, transfer). 
An account is just an entity with string ID, balance and limit (which puts the upper boundary for balance).

### Stack:
* DI: **Koin**
* Server: **Ktor**
* Persistence: **jOOQ**
* Migration: **Flyway**
* Config: **Hoplite**

### Instructions
Run PostgresSQL Docker container by command:
~~~~
docker-compose up -d
~~~~

Build and run application by command:
~~~~
./gradlew clean build run
~~~~

Make requests such as:
~~~~
curl --location --request GET 'http://localhost:8080/account'
~~~~
or
~~~~
curl --location --request POST 'http://localhost:8080/account' \
--header 'Content-Type: application/json' \
--data-raw '{"id": "555111","balance": 100,"limit": 22200}'
~~~~

The entire API description is available on http://localhost:8080/openapi
