package me.toyproject.whatmoviedataimport.batch;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterWrite;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@EnableBatchProcessing
@SpringBootTest
public class TestBatchConfig {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;


    @AfterEach
    void cleanUp() {
        jobRepositoryTestUtils.removeJobExecutions();
    }

    @Test
    public void givenTest() throws Exception {

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();


    }



}
