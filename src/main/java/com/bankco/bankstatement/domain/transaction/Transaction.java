package com.bankco.bankstatement.domain.transaction;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a single transaction entry for a customer
 * (like debit or credit).
 */
@Table("transactions")
public class Transaction {

    @Id
    private Long id;

    private Long customerId;      // Foreign key to customers table
    private LocalDate date;       // Date of transaction
    private String description;   // e.g. "ATM Withdrawal", "Salary Credit"
    private BigDecimal amount;    // Transaction amount
    private String type;          // "DEBIT" or "CREDIT"
    private BigDecimal balance;   // Balance after this transaction

    public Transaction() {
    }

    public Transaction(Long id, Long customerId, LocalDate date,
                       String description, BigDecimal amount,
                       String type, BigDecimal balance) {
        this.id = id;
        this.customerId = customerId;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.balance = balance;
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
