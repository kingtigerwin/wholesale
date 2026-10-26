package com.anz.wholesale.integration;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;

public class TransactionsIntegrationTest extends IntegrationTestBase {

    private static final String TRANSACTIONS = "/management/accounts/{accountId}/transactions";

    @Test
    public void shouldReturnAccountTransactionsGivenAdminToken() {
        given()
                .header("Authorization", loginAs(ADMIN))
                .get(TRANSACTIONS, accountIdOf("585309209"))
                .then()
                .statusCode(200)
                .body("$", hasSize(10))
                .body("creditAmount", containsInAnyOrder(
                        954.98, 745.98, 342.22, 34.11, 665.44, 88.98, 22.12, 234.43, 3456.23, 65.33))
                .body("debitAmount", everyItem(equalTo(0.0)))
                .body("debitOrCredit", everyItem(equalTo("Credit")))
                .body("currency", everyItem(equalTo("SGD")))
                .body("valueDate", everyItem(equalTo("2012-01-12")));
    }

    @Test
    public void shouldReturnTransactionNotExistGivenAccountWithoutTransactions() {
        int accountId = accountIdOf("111111111");

        given()
                .header("Authorization", loginAs(ADMIN))
                .get(TRANSACTIONS, accountId)
                .then()
                .statusCode(200)
                .body("message", equalTo("TRANSACTION_NOT_EXIST"))
                .body("details", equalTo(String.format("Account with id:%s does not have transactions", accountId)));
    }

    @Test
    public void shouldReturnInvalidAccountGivenUnknownAccountId() {
        given()
                .header("Authorization", loginAs(ADMIN))
                .get(TRANSACTIONS, 9999)
                .then()
                .statusCode(400)
                .body("message", equalTo("INVALID_ACCOUNT_ID"));
    }

    @Test
    public void shouldReturnBadRequestGivenNonNumericAccountId() {
        given()
                .header("Authorization", loginAs(ADMIN))
                .get(TRANSACTIONS, "abc")
                .then()
                .statusCode(400)
                .body("message", equalTo("INVALID_PARAMETER"));
    }

    @Test
    public void shouldDenyAccessGivenGroupAdminTokenWithoutWritePermission() {
        given()
                .header("Authorization", loginAs(GROUP_ADMIN))
                .get(TRANSACTIONS, accountIdOf("585309209"))
                .then()
                .statusCode(403)
                .body("message", equalTo("ACCESS_DENIED"));
    }

    @Test
    public void shouldDenyAccessGivenAccountUserToken() {
        given()
                .header("Authorization", loginAs(ACCOUNT_USER))
                .get(TRANSACTIONS, accountIdOf("585309209"))
                .then()
                .statusCode(403)
                .body("message", equalTo("ACCESS_DENIED"));
    }

    @Test
    public void shouldRejectRequestGivenNoToken() {
        given()
                .get(TRANSACTIONS, 1)
                .then()
                .statusCode(403);
    }
}
