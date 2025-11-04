package com.bankco.bankstatement.domain.customer;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Represents a bank customer in the system.
 * This class will be stored in the "customers" table in PostgreSQL.
 */
@Table("customers")
public class Customer {

    @Id
    private Long id;

    private String name;
    private String accountNumber;
    private String address;
    private String email;

    // ✅ Default constructor (required by Spring Data JDBC)
    public Customer() {
    }

    // ✅ Constructor with all fields (we’ll use later)
    public Customer(Long id, String name, String accountNumber, String address, String email) {
        this.id = id;
        this.name = name;
        this.accountNumber = accountNumber;
        this.address = address;
        this.email = email;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
