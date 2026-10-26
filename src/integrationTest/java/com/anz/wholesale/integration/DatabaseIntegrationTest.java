package com.anz.wholesale.integration;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

// Integration tests must run against PostgreSQL (unit tests use H2), so check the app's data really lives there.
public class DatabaseIntegrationTest extends IntegrationTestBase {

    @Test
    public void shouldServeDataMigratedIntoPostgresGivenRunningStack() throws SQLException {
        try (Connection connection = openDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet accounts = statement.executeQuery("select count(*) from accounts")) {
            assertThat(connection.getMetaData().getDatabaseProductName(), equalTo("PostgreSQL"));
            accounts.next();
            int accountsInPostgres = accounts.getInt(1);

            given()
                    .header("Authorization", loginAs(ADMIN))
                    .get("/management/accounts")
                    .then()
                    .statusCode(200)
                    .body("size()", equalTo(accountsInPostgres));
        }
    }
}
