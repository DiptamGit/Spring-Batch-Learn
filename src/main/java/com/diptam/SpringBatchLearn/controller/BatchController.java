package com.diptam.SpringBatchLearn.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("load")
public class BatchController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        Map<String, JobParameter> paramMap = new HashMap<>();
        paramMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameter = new JobParameters(paramMap);

        JobExecution jobExecution = jobLauncher.run(job, jobParameter);
        System.out.println("Job Execution : "+jobExecution.getStatus());

        while (jobExecution.isRunning()){
            System.out.println("Batch is running .. ..");
        }

        return jobExecution.getStatus();
    }
}
