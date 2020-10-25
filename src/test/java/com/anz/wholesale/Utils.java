package com.anz.wholesale;

import com.anz.wholesale.entities.Account;
import com.anz.wholesale.entities.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class Utils {

    public Transaction buildTransaction(Account savedAccount,
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

    public Account buildAccount(String accountName,
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

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
