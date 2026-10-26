package com.anz.wholesale.integration;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class AccountsIntegrationTest extends IntegrationTestBase {

    private static final String ACCOUNTS = "/management/accounts";

    @Test
    public void shouldReturnAllSeededAccountsGivenAdminToken() {
        given()
                .header("Authorization", loginAs(ADMIN))
                .get(ACCOUNTS)
                .then()
                .statusCode(200)
                .body("$", hasSize(8))
                .body("accountNumber", containsInAnyOrder(
                        "585309209", "791066619", "321143048", "347786244",
                        "680168913", "136056165", "453963528", "111111111"));
    }

    @Test
    public void shouldMapAccountFieldsGivenAdminToken() {
        String account = "find { it.accountNumber == '585309209' }";

        given()
                .header("Authorization", loginAs(ADMIN))
                .get(ACCOUNTS)
                .then()
                .statusCode(200)
                .body(account + ".accountName", equalTo("SGSavings726"))
                .body(account + ".accountType", equalTo("Savings"))
                .body(account + ".balanceDate", equalTo("2018-11-08"))
                .body(account + ".currency", equalTo("SGD"))
                .body(account + ".openingAvailBalance", equalTo(84327.51));
    }

    @Test
    public void shouldReturnAccountsGivenGroupAdminToken() {
        given()
                .header("Authorization", loginAs(GROUP_ADMIN))
                .get(ACCOUNTS)
                .then()
                .statusCode(200)
                .body("$", hasSize(8));
    }

    @Test
    public void shouldDenyAccessGivenAccountUserToken() {
        given()
                .header("Authorization", loginAs(ACCOUNT_USER))
                .get(ACCOUNTS)
                .then()
                .statusCode(403)
                .body("message", equalTo("ACCESS_DENIED"));
    }

    @Test
    public void shouldRejectRequestGivenNoToken() {
        given()
                .get(ACCOUNTS)
                .then()
                .statusCode(403);
    }
}
