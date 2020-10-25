
# ANZ Wholesale RESTful Backend Sample application
## Tech Stack:
* SpringBoot
* Spring Security + JWT
* Docker
* PostgreSQL
* Lombok
* Mapstruct
* Spring Data JPA
* H2 in-memory DB
* Mockito
* MockMvc
* Flyway

## Topics
1. [How to run this application](#How-to-run-this-application)
2. [How to access the spring boot restful application](#How-to-access-the-spring-boot-restful-application)
3. [PostgreSQL version based Wholesale Application](#PostgreSQL-version-based-Wholesale-Application)
4. [Advantages of this application](#Advantages-of-this-application)

### This is the springBoot based RESTful API. It supports the below functions:

* Application support H2 in-mem DB and Postgres DB, by default H2 is being used. If you want to use postgres DB, application-postgres.properties shall be used.
* List all accounts
* List all transactions by account id


## How to run this application

* Navigate the the root folder /wholesale under the command line
* run the command to build the whole project: **gradle clean build**
* run the command to start the application: **java -jar ./build/libs/wholesale-0.0.1-SNAPSHOT.jar**

## How to access the spring boot restful application
### Application by default provide the below 3 users:
#### username/password : tiger/password  (normal user without any admin role). this user can login successfully but not allowed to get account list nor transaction list
#### username/password : sisi/password  (admin with role admin and read/write permission). This use can login successfully and be able to access all the endpoints.
#### username/password : cathy/password  (group admin with role group admin and only read permission) This user can login successfully and allowed to get account list but not allowed to get transaction list.


### Please find the postman collection file in postman folder and import into your postman to check more details.



## PostgreSQL version based Wholesale Application
### I also prepared PostgreSQL version. Please follow below steps if you want to try PostgreSQL version
* Navigate the the root folder /wholesale under the command line
* rename **application.properties** to another name e.g. **application_backup.properties**
* rename **application-postgresql.properties** to **application.properties**
* setup postgres DB in local
* run the command to build the whole project: **gradle clean build**
* when the above step is done, run the command **java -jar ./build/libs/wholesale-0.0.1-SNAPSHOT.jar**

## Advantages of this application
* Hibernate builds the entity layer to connect PostgreSQL database server
* Flyway prepared the initial data.
* Spring Data JPA builds the repository layer (DAO) & H2 in-mem database used as Unit Test to test this layer
* Mapstruct used to map data between entities and dtos
* Mockito and MockMvc unit test service and controller layer
* All the exceptions can be centrally handled in one place (ControllerExceptionHandler.java)
* Lombok makes our life easierIt automatically generates getter,setter, constructor, hashcode, log etc.
* Dockerfile utilize multi stage and put application files into docker instead of using fat jar.
* Utilize Spring security to implement authentication and authorization. 
