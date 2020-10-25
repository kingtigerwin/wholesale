package com.anz.wholesale.controllers;

import com.anz.wholesale.auth.ApplicationUserService;
import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.dtos.transaction.TransactionGetDto;
import com.anz.wholesale.entities.Account;
import com.anz.wholesale.entities.Transaction;
import com.anz.wholesale.services.AccountService;
import com.anz.wholesale.utils.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountManagementController.class)
@Import(AccountManagementController.class)
@ContextConfiguration(classes = { Utility.class})
public class AccountManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private ApplicationUserService applicationUserService;

    @Autowired
    private Utility utility;


    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void shouldReturnAccountListGivenAccountsExists() throws Exception {
        AccountGetDto accountGetDto = utility.buildAccountGetDto(1L,
                "accName",
                "accNumber",
                "Savings",
                LocalDate.now(),
                "AUD",
                11f);

        BDDMockito.given(accountService.getAccounts()).willReturn(List.of(accountGetDto));
        mockMvc.perform(get("/management/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].accountName").value("accName"))
                .andExpect(jsonPath("$.[0].accountNumber").value("accNumber"))
                .andExpect(jsonPath("$.[0].accountType").value("Savings"))
                .andExpect(jsonPath("$.[0].currency").value("AUD"))
                .andExpect(jsonPath("$.[0].openingAvailBalance").value(11f));
    }

    @Test
    @WithMockUser(authorities={"account:write"})
    public void shouldReturnTransactionsGivenAccountHasTransactions() throws Exception {
        TransactionGetDto transactionGetDto = utility.buildTransactionGetDto(1L,
                1d,
                "AUD",
                "Credit",
                "transactionnarrative");
        BDDMockito.given(accountService.getTransactionsByUser(1L)).willReturn(List.of(transactionGetDto));
        mockMvc.perform(get("/management/accounts/"+1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].creditAmount").value("1.0"))
                .andExpect(jsonPath("$.[0].currency").value("AUD"))
                .andExpect(jsonPath("$.[0].transactionNarrative").value("transactionnarrative"));
    }
}
