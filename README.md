
# ANZ Wholesale RESTful Backend Sample application
## Tech Stack:
* Java 25
* SpringBoot 4.1
* Gradle 9 (wrapper)
* Spring Security + JWT
* Docker
* Docker compose
* PostgreSQL
* Lombok
* Mapstruct
* Spring Data JPA
* H2 in-memory DB
* Mockito
* MockMvc
* Flyway
* Testcontainers + REST Assured (integration tests)
* GitHub Actions (CI/CD)

## Topics
1. [How to run this application](#How-to-run-this-application)
2. [How to access the spring boot restful application](#How-to-access-the-spring-boot-restful-application)
3. [PostgreSQL version based Wholesale Application](#PostgreSQL-version-based-Wholesale-Application)
4. [Advantages of this application](#Advantages-of-this-application)

### This is the springBoot based RESTful API. It supports the below functions:

* Application support H2 in-mem DB and Postgres DB, by default postgres DB is being used. If you want to use H2 in-mem DB, application-h2.properties shall be used.
* List all accounts
* List all transactions by account id


## How to run this application

* Navigate the the root folder /wholesale under the command line
* Run the command to build the whole project: **./gradlew clean build** (Gradle downloads a Java 25 toolchain automatically if one is not installed)
* Either run the command to start the application: **java -jar ./build/libs/wholesale-0.0.1-SNAPSHOT.jar**
* Or run **docker compose up**, then application and postgres DB are both up and running.

## How to run the integration tests

* Docker must be running. Run **./gradlew integrationTest**
* It builds the jar, starts the app and a fresh Postgres from **docker-compose.it.yaml** (via Testcontainers), then calls the real endpoints with REST Assured. The stack is removed when the tests finish.
* **./gradlew test** still runs only the fast unit tests and does not need Docker.
* Unit tests always run on in-memory H2 and integration tests always run on PostgreSQL. A test in each suite fails if that ever changes.
* With Colima instead of Docker Desktop, point Testcontainers at the Colima socket first:
  **export DOCKER_HOST=unix://$HOME/.colima/default/docker.sock TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock**

## CI/CD (GitHub Actions)

The pipeline is defined in **.github/workflows/ci-cd.yml**:

* Pull requests to master: build, unit tests and integration tests.
* Pushes to master: the same checks, then the Docker image is pushed to ECR (tagged with the commit SHA and `latest`) and deployed to the ECS service.
* The image packages the jar from the build job, so the deployed jar is the one the tests ran against. The **Dockerfile** does not compile anything; to build the image locally run **./gradlew bootJar** first, then **docker build -t wholesale .**

One-time setup in GitHub (Settings → Secrets and variables → Actions → Variables):

* **AWS_ROLE_ARN**: IAM role GitHub Actions assumes through OIDC. It needs ECR push and ECS deploy permissions (including `iam:PassRole` for the task's roles).
* **ECS_CLUSTER**, **ECS_SERVICE**: where the app runs.
* **ECS_TASK_DEFINITION**: task definition family. The workflow takes its latest revision and only swaps the image of the `accountQuery` container.
* Optionally add protection rules (e.g. required reviewers) to the **production** environment to gate deploys.

## How to access the spring boot restful application
### Application by default provide the below 3 users:
#### username/password : tiger/password  (normal user without any admin role). this user can login successfully but not allowed to get account list nor transaction list
#### username/password : sisi/password  (admin with role admin and read/write permission). This use can login successfully and be able to access all the endpoints.
#### username/password : cathy/password  (group admin with role group admin and only read permission) This user can login successfully and allowed to get account list but not allowed to get transaction list.


### Please find the postman collection file in postman folder and import into your postman to check more details.


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
* Docker compose has been used in local to easily set up the local environment.
