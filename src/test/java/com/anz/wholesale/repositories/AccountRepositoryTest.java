package com.anz.wholesale.repositories;

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

    @Test
    public void shouldReturnAccountListGivenAccountsHasBeenInserted() {
        // Create a new account.
        Account account1 = buildAccount("SGSavings726", "585309209", "Savings", LocalDate.now(), "SGD", 33.44f);
        Account account2 = buildAccount("SGSavings724", "585309205", "Savings", LocalDate.now(), "SGD", 33.44f);
        Account savedAccount = accountRepository.save(account1);
        accountRepository.save(account2);


        List<Account> accountList = accountRepository.findAll();
        assertNotNull(accountList);
        assertTrue(accountList.size() >= 2);
    }


    @Test
    public void shouldReturnAccountRelatedTransactionsGivenAccountId() {
        // Create a new account.
        Account account = buildAccount("SGSavings726", "585309209", "Savings", LocalDate.now(), "SGD", 33.44f);
        Account savedAccount = accountRepository.save(account);

        // Create a new transaction associated with account created above.
        Transaction transaction1 = buildTransaction(savedAccount, 12.3D, "SGD", LocalDate.now());
        transactionRepository.save(transaction1);

        // Create one more transaction associated with account created above.
        Transaction transaction2 = buildTransaction(savedAccount, 12.3D, "SGD", LocalDate.now());
        transactionRepository.save(transaction2);

        Set<Transaction> transactions = accountRepository.findAccountAndRelatedTransactionsByAccountId(savedAccount.getId()).getTransactions();
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }


    private Transaction buildTransaction(Account savedAccount,
                                         Double creditAmount,
                                         String currency,
                                         LocalDate valueDate) {

        Transaction transaction = new Transaction();
        transaction.setAccount(savedAccount);
        transaction.setCreditAmount(creditAmount);
        transaction.setCurrency(currency);
        transaction.setValueDate(valueDate);
        return transaction;
    }

    private Account buildAccount(String accountName,
                                 String accountNumber,
                                 String accountType,
                                 LocalDate balanceDate,
                                 String currency,
                                 Float openingAvailBalance) {

        Account account = new Account();
        account.setAccountName(accountName);
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setBalanceDate(balanceDate);
        account.setCurrency(currency);
        account.setOpeningAvailBalance(openingAvailBalance);
        return account;
    }
}
