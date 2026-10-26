package com.anz.wholesale.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationFlowTest {

    @Autowired
    private MockMvc mockMvc;

    private String login(String username, String password) throws Exception {
        return mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.AUTHORIZATION);
    }

    @Test
    public void shouldIssueTokenThatGrantsAccessGivenAdminCredentials() throws Exception {
        String token = login("sisi", "password");
        assertTrue(token.startsWith("Bearer "));

        mockMvc.perform(get("/management/accounts").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        mockMvc.perform(get("/management/accounts/1/transactions").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEnforcePermissionsGivenGroupAdminToken() throws Exception {
        String token = login("cathy", "password");

        mockMvc.perform(get("/management/accounts").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isOk());
        mockMvc.perform(get("/management/accounts/1/transactions").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnBadRequestGivenNonNumericAccountId() throws Exception {
        String token = login("sisi", "password");

        mockMvc.perform(get("/management/accounts/abc/transactions").header(HttpHeaders.AUTHORIZATION, token))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnauthorizedGivenUnknownUser() throws Exception {
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"nobody\",\"password\":\"password\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorizedGivenInvalidToken() throws Exception {
        mockMvc.perform(get("/management/accounts").header(HttpHeaders.AUTHORIZATION, "Bearer not-a-jwt"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("INVALID_TOKEN"));
    }

    @Test
    public void shouldRejectRequestGivenNoToken() throws Exception {
        mockMvc.perform(get("/management/accounts"))
                .andExpect(status().isForbidden());
    }
}
