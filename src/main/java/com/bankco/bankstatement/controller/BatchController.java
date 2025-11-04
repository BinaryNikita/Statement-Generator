package com.bankco.bankstatement.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller to manually trigger the batch job.
 */
@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job statementJob;

    @PostMapping("/run")
    public String runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis()) // ensures unique job
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(statementJob, jobParameters);

            return "✅ Batch Job Started. Status: " + execution.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error starting batch job: " + e.getMessage();
        }
    }
}
