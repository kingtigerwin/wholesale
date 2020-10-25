package com.anz.wholesale.controllers;

import com.anz.wholesale.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountManagementController.class)
public class AccountManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void shouldReturnAccountListGivenAccountsExists() {

//        BDDMockito.given(accountService.getAccounts()).willReturn();
    }
}
