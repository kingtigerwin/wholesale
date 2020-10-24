package com.anz.wholesale.dtos.transaction;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionGetDto {

    private Long id;

    private LocalDate valueDate;

    private String currency;

    private Double debitAmount;

    private Double creditAmount;

    private String debitOrCredit;

    private String transactionNarrative;
}
