package com.payoneer.jobmgmtservice.job.scheduler;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import lombok.extern.slf4j.Slf4j;

@Configuration
@ConditionalOnExpression("'${using.spring.schedulerFactory}'=='false'")
@Slf4j
public class QrtzSchedulerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        log.info("Hello world from Quartz...");
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        log.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

//    @Bean
//    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory) throws SchedulerException {
//        log.debug("Getting a handle to the Scheduler");
//        Scheduler scheduler = factory.getScheduler();
//        scheduler.scheduleJob(job, trigger);
//
//        log.debug("Starting Scheduler threads");
//        scheduler.start();
//        return scheduler;
//    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

//    @Bean
//    public JobDetail jobDetail() {
//
//        return newJob().ofType(SampleJob.class).storeDurably().withIdentity(JobKey.jobKey("Qrtz_Job_Detail")).withDescription("Invoke Sample Job service...").build();
//    }
//
//    @Bean
//    public Trigger trigger(JobDetail job) {
//    	String exp = "0 0 0 1/1 * ? *";
//        int frequencyInSec = 10;
//        log.info("Configuring trigger to fire every {} ", frequencyInSec);
//
//        return newTrigger().forJob(job).withIdentity(TriggerKey.triggerKey("Qrtz_Trigger")).withDescription("Sample trigger").withSchedule(CronScheduleBuilder.cronSchedule(exp)).build();
//    }
}
