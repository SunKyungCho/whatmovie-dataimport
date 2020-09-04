package me.toyproject.whatmoviedataimport.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


//@Component
//@Slf4j
//@RequiredArgsConstructor
public class MovieDetailImportJob {

    JobRepository jobRepository;

    public void test() {

        JobExecution lastJobExecution = jobRepository.getLastJobExecution("", new JobParameters());
        BatchStatus status = lastJobExecution.getStatus();
    }

//    private final JobLauncher jobLauncher;
//    private final Job job;
//
////    @Scheduled(cron = "* * * * *")
//    public void executeInternal() throws JobExecutionException {
//        try {
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addLong("time", System.currentTimeMillis())
//                    .toJobParameters();
//            JobExecution run = jobLauncher.run(job, jobParameters);
//            log.info("End Process. ");
//        } catch (JobExecutionAlreadyRunningException e) {
//            e.printStackTrace();
//        } catch (JobRestartException e) {
//            e.printStackTrace();
//        } catch (JobInstanceAlreadyCompleteException e) {
//            e.printStackTrace();
//        } catch (JobParametersInvalidException e) {
//            e.printStackTrace();
//        }
//    }
}
