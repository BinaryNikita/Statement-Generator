package com.bankco.bankstatement.config;

import com.bankco.bankstatement.domain.customer.Customer;
import com.bankco.bankstatement.domain.customer.CustomerRepository;
import com.bankco.bankstatement.service.StatementService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Collections;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StatementService statementService;

    // 1️⃣ Reader - fetches customers from DB
    
    @Bean
    public ItemReader<Customer> customerReader() {
    return new RepositoryItemReaderBuilder<Customer>()
            .name("customerReader")
            .repository(customerRepository)
            .methodName("findAll") // this calls findAll(Pageable)
            .pageSize(10)          // number of records per page
            .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
            .build();
    }


    // 2️⃣ Processor - generates statement for each customer
    @Bean
    public ItemProcessor<Customer, Customer> statementProcessor() {
        return customer -> {
            statementService.generateStatementForCustomer(customer);
            return customer;
        };
    }

    // 3️⃣ Writer - log processed customer names
    @Bean
    public ItemWriter<Customer> customerWriter() {
        return customers -> customers.forEach(c ->
                System.out.println("✅ Processed statement for: " + c.getName())
        );
    }

    // 4️⃣ Define the step (chunk = 1 means 1 customer per transaction)
    @Bean
    public Step statementStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("statementStep", jobRepository)
                .<Customer, Customer>chunk(1, transactionManager)
                .reader(customerReader())
                .processor(statementProcessor())
                .writer(customerWriter())
                .taskExecutor(new SimpleAsyncTaskExecutor("batch-thread-"))
                .throttleLimit(200) // 🔥 Run 4 customers in parallel
                .build();
    }

    // 5️⃣ Define the job
    @Bean
    public Job statementJob(JobRepository jobRepository, Step statementStep) {
        return new JobBuilder("statementJob", jobRepository)
                .start(statementStep)
                .build();
    }
}
