package com.anz.wholesale.repositories;

import com.anz.wholesale.utils.Utility;
import com.anz.wholesale.WholesaleApplication;
import com.anz.wholesale.entities.Account;
import com.anz.wholesale.entities.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WholesaleApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private Utility utility;

    @Test
    public void shouldReturnAccountListGivenAccountsHasBeenInserted() {
        // Create a new account.
        Account account1 = utility.buildAccount("SGSavings726", "585309209", "Savings", LocalDate.now(), "SGD", 33.44f);
        Account account2 = utility.buildAccount("SGSavings724", "585309205", "Savings", LocalDate.now(), "SGD", 33.44f);
        Account savedAccount = accountRepository.save(account1);
        accountRepository.save(account2);


        List<Account> accountList = accountRepository.findAll();
        assertNotNull(accountList);
        assertTrue(accountList.size() >= 2);
    }


    @Test
    public void shouldReturnAccountRelatedTransactionsGivenAccountId() {
        // Given
        // Create a new account.
        Account account = utility.buildAccount("SGSavings726", "585309209", "Savings", LocalDate.now(), "SGD", 33.44f);
        Account savedAccount = accountRepository.save(account);

        // Create a new transaction associated with account created above.
        Transaction transaction1 = utility.buildTransaction(savedAccount, 12.3D, "SGD", LocalDate.now());
        transactionRepository.save(transaction1);

        // Create one more transaction associated with account created above.
        Transaction transaction2 = utility.buildTransaction(savedAccount, 12.3D, "SGD", LocalDate.now());
        transactionRepository.save(transaction2);

        // When
        Set<Transaction> transactions = accountRepository.findAccountAndRelatedTransactionsByAccountId(savedAccount.getId()).get().getTransactions();

        // Then
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }



}
