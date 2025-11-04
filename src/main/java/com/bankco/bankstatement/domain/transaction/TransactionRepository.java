package com.bankco.bankstatement.domain.transaction;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for accessing Transaction data from the database.
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    // ✅ Fetch all transactions for a customer within a date range
    @Query("SELECT * FROM transactions WHERE customer_id = :customerId AND date BETWEEN :startDate AND :endDate ORDER BY date ASC")
    List<Transaction> findByCustomerIdAndDateRange(Long customerId, LocalDate startDate, LocalDate endDate);

        @Query("SELECT * FROM transactions WHERE customer_id = :customerId")
    List<Transaction> findByCustomerId(Long customerId);
}
