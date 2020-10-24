package com.anz.wholesale.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@ToString(exclude = "account")
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Column(name = "value_date", nullable = false)
    private LocalDate valueDate;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "debit_amount")
    private Double debitAmount;

    @Column(name = "credit_amount")
    private Double creditAmount;

    @Column(name = "debit_or_credit")
    private String debitOrCredit;

    @Column(name = "transaction_narrative")
    private String transactionNarrative;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
