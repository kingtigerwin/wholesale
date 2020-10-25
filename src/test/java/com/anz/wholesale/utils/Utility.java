package com.anz.wholesale.utils;

import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.dtos.transaction.TransactionGetDto;
import com.anz.wholesale.entities.Account;
import com.anz.wholesale.entities.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class Utility {

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

    public AccountGetDto buildAccountGetDto(Long id,
                                            String accountName,
                                            String accountNumber,
                                            String accountType,
                                            LocalDate balanceDate,
                                            String currency,
                                            Float openingAvailBalance) {

        AccountGetDto account = new AccountGetDto();
        account.setId(id);
        account.setAccountName(accountName);
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType);
        account.setBalanceDate(balanceDate);
        account.setCurrency(currency);
        account.setOpeningAvailBalance(openingAvailBalance);
        return account;
    }

    public TransactionGetDto buildTransactionGetDto(Long id,
                                                    Double creditAmount,
                                                    String currency,
                                                    String debitOrCredit,
                                                    String transactionNarrative) {
        TransactionGetDto transactionGetDto = new TransactionGetDto();
        transactionGetDto.setId(id);
        transactionGetDto.setCreditAmount(creditAmount);
        transactionGetDto.setCurrency(currency);
        transactionGetDto.setDebitOrCredit(debitOrCredit);
        transactionGetDto.setTransactionNarrative(transactionNarrative);
        return transactionGetDto;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
