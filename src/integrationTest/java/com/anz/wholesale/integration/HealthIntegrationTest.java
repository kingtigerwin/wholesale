package com.anz.wholesale.integration;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HealthIntegrationTest extends IntegrationTestBase {

    @Test
    public void shouldReportUpGivenNoToken() {
        given()
                .get("/actuator/health")
                .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
}
