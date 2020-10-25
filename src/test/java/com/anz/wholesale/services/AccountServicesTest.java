package com.anz.wholesale.services;

import com.anz.wholesale.utils.Utility;
import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.dtos.transaction.TransactionGetDto;
import com.anz.wholesale.entities.Account;
import com.anz.wholesale.entities.Transaction;
import com.anz.wholesale.exceptions.InvalidAccountException;
import com.anz.wholesale.exceptions.TransactionNotExistException;
import com.anz.wholesale.mappers.AccountMapper;
import com.anz.wholesale.mappers.AccountMapperImpl;
import com.anz.wholesale.mappers.TransactionMapper;
import com.anz.wholesale.mappers.TransactionMapperImpl;
import com.anz.wholesale.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {AccountMapperImpl.class, TransactionMapperImpl.class, Utility.class})
public class AccountServicesTest {

    @Mock
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private Utility utility;

    AccountService accountService;

    @BeforeEach
    void setup() {
        accountService = new AccountService(accountRepository, accountMapper, transactionMapper);
    }

    @Test
    public void shouldReturnAccountListGivenAccountsExist() {
        Account account1 = utility.buildAccount("accName",
                "accNumber",
                "Savings",
                LocalDate.now(),
                "AUD",
                33F);
        Account account2 = utility.buildAccount("accName",
                "accNumber",
                "Savings",
                LocalDate.now(),
                "AUD",
                33F);

        when(accountRepository.findAll()).thenReturn(List.of(account1, account2));
        List<AccountGetDto> returnedAccountList = accountService.getAccounts();
        assertNotNull(returnedAccountList);
        assertEquals(2, returnedAccountList.size());
    }

    @Test
    public void shouldThrowExceptionGivenInvalidAccountId() {
        when(accountRepository.findAccountAndRelatedTransactionsByAccountId(any())).thenReturn(Optional.empty());
        assertThrows(InvalidAccountException.class, () -> accountService.getTransactionsByUser(111L)
        );
    }

    @Test
    public void shouldThrowExceptionGivenTransactionNotExist() {
        Account account = utility.buildAccount("accName",
                "accNumber",
                "Savings",
                LocalDate.now(),
                "AUD",
                33F);

        when(accountRepository.findAccountAndRelatedTransactionsByAccountId(any())).thenReturn(Optional.of(account));
        assertThrows(TransactionNotExistException.class, () -> accountService.getTransactionsByUser(111L)
        );
    }

    @Test
    public void shouldReturnTransactionsGivenAccountHasTransactions() {
        Account account = utility.buildAccount("accName",
                "accNumber",
                "Savings",
                LocalDate.now(),
                "AUD",
                33F);
        Transaction transaction1 = utility.buildTransaction(account, 11d, "AUD", LocalDate.now());
        Transaction transaction2 = utility.buildTransaction(account, 12d, "AUD", LocalDate.now());
        account.setTransactions(Set.of(transaction1, transaction2));

        when(accountRepository.findAccountAndRelatedTransactionsByAccountId(any())).thenReturn(Optional.of(account));
        List<TransactionGetDto> transactions = accountService.getTransactionsByUser(1L);
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }

}
