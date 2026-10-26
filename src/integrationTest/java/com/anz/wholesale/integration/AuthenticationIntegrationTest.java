package com.anz.wholesale.integration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;

public class AuthenticationIntegrationTest extends IntegrationTestBase {

    @ParameterizedTest
    @ValueSource(strings = {ADMIN, GROUP_ADMIN, ACCOUNT_USER})
    public void shouldIssueBearerTokenGivenValidCredentials(String username) {
        login(username, PASSWORD)
                .then()
                .statusCode(200)
                .header("Authorization", startsWith("Bearer "));
    }

    @Test
    public void shouldRejectLoginGivenWrongPassword() {
        login(ADMIN, "wrong-password")
                .then()
                .statusCode(401)
                .header("Authorization", equalTo(null));
    }

    @Test
    public void shouldRejectLoginGivenUnknownUser() {
        login("nobody", PASSWORD)
                .then()
                .statusCode(401)
                .header("Authorization", equalTo(null));
    }

    @Test
    public void shouldIssueTokenWithUserClaimsGivenAdminCredentials() {
        JsonPath claims = claimsOf(loginAs(ADMIN));

        assertThat(claims.getString("sub"), equalTo(ADMIN));
        assertThat(claims.getList("authorities.authority", String.class),
                hasItems("ROLE_ADMIN", "account:read", "account:write"));
        long issuedAt = claims.getLong("iat");
        long expiresAt = claims.getLong("exp");
        assertThat(expiresAt, greaterThan(issuedAt));
        assertThat(expiresAt - issuedAt, lessThanOrEqualTo(ChronoUnit.DAYS.getDuration().toSeconds()));
    }

    @Test
    public void shouldIssueTokenWithoutAdminAuthoritiesGivenAccountUserCredentials() {
        JsonPath claims = claimsOf(loginAs(ACCOUNT_USER));

        assertThat(claims.getList("authorities.authority", String.class), hasItems("ROLE_ACCOUNT"));
        assertThat(claims.getList("authorities.authority", String.class), not(hasItems("ROLE_ADMIN")));
    }

    @Test
    public void shouldRejectRequestGivenTokenWithTamperedClaims() {
        // Escalate tiger's token to ROLE_ADMIN but keep the original signature.
        String[] parts = loginAs(ACCOUNT_USER).substring("Bearer ".length()).split("\\.");
        String forgedPayload = base64Url("{\"sub\":\"tiger\",\"authorities\":[{\"authority\":\"ROLE_ADMIN\"},{\"authority\":\"account:write\"}]}");
        String forgedToken = parts[0] + "." + forgedPayload + "." + parts[2];

        given()
                .header("Authorization", "Bearer " + forgedToken)
                .get("/management/accounts")
                .then()
                .statusCode(401)
                .body("message", equalTo("INVALID_TOKEN"));
    }

    @Test
    public void shouldRejectRequestGivenTokenSignedWithDifferentKey() {
        String token = Jwts.builder()
                .subject(ADMIN)
                .claim("authorities", List.of(Map.of("authority", "ROLE_ADMIN")))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor("a-completely-different-secret-key-of-enough-length".getBytes(StandardCharsets.UTF_8)))
                .compact();

        given()
                .header("Authorization", "Bearer " + token)
                .get("/management/accounts")
                .then()
                .statusCode(401)
                .body("message", equalTo("INVALID_TOKEN"));
    }

    @Test
    public void shouldRejectRequestGivenExpiredToken() throws IOException {
        String token = Jwts.builder()
                .subject(ADMIN)
                .claim("authorities", List.of(Map.of("authority", "ROLE_ADMIN")))
                .issuedAt(Date.from(Instant.now().minus(2, ChronoUnit.DAYS)))
                .expiration(Date.from(Instant.now().minus(1, ChronoUnit.DAYS)))
                .signWith(Keys.hmacShaKeyFor(appSecretKey().getBytes()))
                .compact();

        given()
                .header("Authorization", "Bearer " + token)
                .get("/management/accounts")
                .then()
                .statusCode(401)
                .body("message", equalTo("INVALID_TOKEN"));
    }

    @Test
    public void shouldRejectRequestGivenMalformedToken() {
        given()
                .header("Authorization", "Bearer not-a-jwt")
                .get("/management/accounts")
                .then()
                .statusCode(401)
                .body("message", equalTo("INVALID_TOKEN"));
    }

    @Test
    public void shouldTreatRequestAsAnonymousGivenTokenWithoutBearerPrefix() {
        String rawToken = loginAs(ADMIN).substring("Bearer ".length());

        given()
                .header("Authorization", rawToken)
                .get("/management/accounts")
                .then()
                .statusCode(403);
    }

    @Test
    public void shouldReuseOneTokenAcrossStatelessRequestsGivenAdminLogin() {
        String token = loginAs(ADMIN);
        int accountId = accountIdOf("585309209");

        given().header("Authorization", token).get("/management/accounts").then().statusCode(200);
        given().header("Authorization", token).get("/management/accounts/{id}/transactions", accountId).then().statusCode(200);
        given().header("Authorization", token).get("/management/accounts").then().statusCode(200);
        // No session is created, so the same request without the token is anonymous again.
        given().get("/management/accounts").then().statusCode(403);
    }

    private static JsonPath claimsOf(String bearerToken) {
        String payload = bearerToken.substring("Bearer ".length()).split("\\.")[1];
        return JsonPath.from(new String(Base64.getUrlDecoder().decode(payload), StandardCharsets.UTF_8));
    }

    private static String base64Url(String json) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes(StandardCharsets.UTF_8));
    }

    private static String appSecretKey() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(in);
        }
        return properties.getProperty("jwt.secretKey");
    }
}
