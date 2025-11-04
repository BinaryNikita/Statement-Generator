package com.bankco.bankstatement.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class triggers the batch job automatically when the app starts.
 */
@Component
public class BatchJobRunner implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job statementJob;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("🚀 Starting batch job for customer statement generation...");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis()) // ensures a unique job run each time
                .toJobParameters();

        JobExecution execution = jobLauncher.run(statementJob, jobParameters);

        System.out.println("✅ Job Status: " + execution.getStatus());
    }
}
