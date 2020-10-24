package com.anz.wholesale.dtos.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPutDto {
    private String accountType;
    private LocalDate balanceDate;
    private String currency;
    private double openingAvailBalance;
}
