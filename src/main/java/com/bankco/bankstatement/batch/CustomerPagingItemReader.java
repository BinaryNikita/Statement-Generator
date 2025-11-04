package com.bankco.bankstatement.batch;

import com.bankco.bankstatement.domain.customer.Customer;
import com.bankco.bankstatement.domain.customer.CustomerRepository;
import org.springframework.batch.item.*;

import java.util.List;

/**
 * Simple ItemReader that reads customers in pages using offset.
 * Implements ItemStream so it can store lastOffset for restarts.
 */
public class CustomerPagingItemReader implements ItemReader<Customer>, ItemStream {

    private final CustomerRepository repository;
    private final int pageSize;

    private int currentOffset = 0;
    private List<Customer> currentPage;
    private int indexInPage = 0;
    private String name = "customer-paging-reader";

    public CustomerPagingItemReader(CustomerRepository repository, int pageSize) {
        this.repository = repository;
        this.pageSize = pageSize;
    }

    @Override
    public Customer read() throws Exception {
        if (currentPage == null || indexInPage >= currentPage.size()) {
            // fetch next page
            currentPage = repository.findCustomersBatch(pageSize, currentOffset);
            indexInPage = 0;
            currentOffset += currentPage.size();
            if (currentPage.isEmpty()) {
                return null; // EOF
            }
        }

        return currentPage.get(indexInPage++);
    }

    // ItemStream methods for restartability
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey(name + ".offset")) {
            currentOffset = executionContext.getInt(name + ".offset");
        } else {
            currentOffset = 0;
        }
        currentPage = null;
        indexInPage = 0;
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // persist current offset for restart
        executionContext.putInt(name + ".offset", currentOffset - (currentPage != null ? currentPage.size() - indexInPage : 0));
    }

    @Override
    public void close() throws ItemStreamException {
        // no-op
    }
}
