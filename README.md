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

Then build and run application by command:
~~~~
./gradlew clean build run
~~~~

