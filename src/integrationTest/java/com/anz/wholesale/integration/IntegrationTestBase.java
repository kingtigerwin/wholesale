package com.anz.wholesale.integration;

import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.config.JsonPathConfig;
import org.testcontainers.containers.ComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Starts the app and Postgres from docker-compose.it.yaml once per test run and points REST Assured at the app.
 * The stack is shared by all integration test classes and removed by Testcontainers when the JVM exits.
 */
public abstract class IntegrationTestBase {

    protected static final String PASSWORD = "password";
    protected static final String ADMIN = "sisi";          // ROLE_ADMIN, account:read, account:write
    protected static final String GROUP_ADMIN = "cathy";   // ROLE_GROUPADMIN, account:read
    protected static final String ACCOUNT_USER = "tiger";  // ROLE_ACCOUNT

    private static final String APP_SERVICE = "app";
    private static final int APP_PORT = 8080;
    private static final String DB_SERVICE = "db-postgres";
    private static final int DB_PORT = 5432;

    private static final ComposeContainer ENVIRONMENT = new ComposeContainer(new File("docker-compose.it.yaml"))
            .withBuild(true)
            .withExposedService(DB_SERVICE, DB_PORT, Wait.forListeningPort())
            .withExposedService(APP_SERVICE, APP_PORT,
                    Wait.forHttp("/actuator/health").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(3)));

    static {
        ENVIRONMENT.start();
        RestAssured.baseURI = "http://" + ENVIRONMENT.getServiceHost(APP_SERVICE, APP_PORT);
        RestAssured.port = ENVIRONMENT.getServicePort(APP_SERVICE, APP_PORT);
        // Read JSON decimals as Double rather than the default Float so balances/amounts compare exactly.
        RestAssured.config = RestAssuredConfig.config()
                .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE));
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    // Direct connection to the app's database, for checks that can't be made through the API.
    protected static Connection openDatabaseConnection() throws SQLException {
        String url = String.format("jdbc:postgresql://%s:%d/postgres",
                ENVIRONMENT.getServiceHost(DB_SERVICE, DB_PORT), ENVIRONMENT.getServicePort(DB_SERVICE, DB_PORT));
        return DriverManager.getConnection(url, "postgres", "mysecretpassword");
    }

    protected static String loginAs(String username) {
        return login(username, PASSWORD)
                .then()
                .statusCode(200)
                .extract()
                .header("Authorization");
    }

    protected static io.restassured.response.Response login(String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body(String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password))
                .post("/login");
    }

    protected static int accountIdOf(String accountNumber) {
        List<Map<String, Object>> accounts = given()
                .header("Authorization", loginAs(ADMIN))
                .get("/management/accounts")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("$");
        return accounts.stream()
                .filter(account -> accountNumber.equals(account.get("accountNumber")))
                .map(account -> (Integer) account.get("id"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No seeded account " + accountNumber));
    }
}
