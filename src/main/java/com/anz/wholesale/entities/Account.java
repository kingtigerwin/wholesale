package com.anz.wholesale.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_name", unique = true, nullable = false)
    private String accountName;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "balance_date", nullable = false)
    private LocalDate balanceDate;

    @Column(name = "currency", unique = true, nullable = false)
    private String currency;

    @Column(name = "opening_avail_balance", unique = true, nullable = false)
    private Float openingAvailBalance;

    @OneToMany(mappedBy = "account")
    private Set<Transaction> transactions;

}
