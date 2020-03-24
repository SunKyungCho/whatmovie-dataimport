package me.toyproject.whatmoviedataimport.config.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SimpleTrigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final SchedulerFactoryBean schedulerFactoryBean;
    private final ApplicationContext context;

    public void addSchedule() {

        try {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("time", new Date());

            JobDetailFactoryBean jobFactoryBean = new JobDetailFactoryBean();
            jobFactoryBean.setJobClass(MovieDetailImportJob.class);
            jobFactoryBean.setDurability(false);
            jobFactoryBean.setApplicationContext(context);
            jobFactoryBean.setName("movie import");
            jobFactoryBean.setGroup("movie");
            jobFactoryBean.setJobDataMap(jobDataMap);
            jobFactoryBean.afterPropertiesSet();

            CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
            triggerFactoryBean.setName("movie import");
            triggerFactoryBean.setGroup("movie");
            triggerFactoryBean.setCronExpression("0/5 * * * * ?");
            triggerFactoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW); //바로 실행
            triggerFactoryBean.afterPropertiesSet();

            JobKey jobKey = new JobKey("movie import", "movie");

            Date date = schedulerFactoryBean.getScheduler().scheduleJob(jobFactoryBean.getObject(), triggerFactoryBean.getObject());

            log.debug("Job with jobKey : {} scheduled successfully at date : {}", jobFactoryBean.getObject().getKey(), date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}