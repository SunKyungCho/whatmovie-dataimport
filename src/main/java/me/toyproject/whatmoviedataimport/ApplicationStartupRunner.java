package me.toyproject.whatmoviedataimport;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler();
//
//        scheduler.start();
//
//        JobDetail job = JobBuilder.newJob(HelloJob.class)
//                .withIdentity("myJob", "group1")
//                .build();
//
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("myTrigger", "group")
//                .startNow()
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withIntervalInSeconds(10)
//                        .repeatForever())
//                .build();
//
//        scheduler.scheduleJob(job, trigger);
    }
}