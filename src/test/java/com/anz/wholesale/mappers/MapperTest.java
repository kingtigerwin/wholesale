package com.anz.wholesale.mappers;

import com.anz.wholesale.dtos.account.AccountGetDto;
import com.anz.wholesale.dtos.transaction.TransactionGetDto;
import com.anz.wholesale.entities.Account;
import com.anz.wholesale.entities.Transaction;
import com.anz.wholesale.utils.Utility;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapperTest {

    private final Utility utility = new Utility();

    @Test
    public void shouldCopyAllFieldsGivenAccountEntity() {
        LocalDate balanceDate = LocalDate.of(2018, 11, 8);
        Account account = utility.buildAccount("SGSavings726", "585309209", "Savings", balanceDate, "SGD", new BigDecimal("84327.51"));
        account.setId(1L);

        AccountGetDto dto = new AccountMapperImpl().fromEntity(account);

        assertEquals(1L, dto.getId());
        assertEquals("SGSavings726", dto.getAccountName());
        assertEquals("585309209", dto.getAccountNumber());
        assertEquals("Savings", dto.getAccountType());
        assertEquals(balanceDate, dto.getBalanceDate());
        assertEquals("SGD", dto.getCurrency());
        assertEquals(84327.51d, dto.getOpeningAvailBalance());
    }

    @Test
    public void shouldCopyAllFieldsGivenTransactionEntity() {
        LocalDate valueDate = LocalDate.of(2012, 1, 12);
        Transaction transaction = utility.buildTransaction(new Account(), 954.98d, "SGD", valueDate);
        transaction.setId(2L);

        TransactionGetDto dto = new TransactionMapperImpl().fromEntity(transaction);

        assertEquals(2L, dto.getId());
        assertEquals(954.98d, dto.getCreditAmount());
        assertEquals("SGD", dto.getCurrency());
        assertEquals(valueDate, dto.getValueDate());
    }
}
