package com.bankco.bankstatement.domain.customer;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Customer data in the database.
 * Spring Data JDBC will automatically generate the implementation.
 */
@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    // ✅ You can later define custom queries here, for example:
    // List<Customer> findByName(String name);

    // Simple offset+limit paging suitable for batch reads
    @Query("SELECT * FROM customers ORDER BY id LIMIT :limit OFFSET :offset")
    List<Customer> findCustomersBatch(int limit, int offset);
}

