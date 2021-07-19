package com.payoneer.jobmgmtservice.job.scheduler;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.time.LocalDateTime;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.payoneer.jobmgmtservice.common.constants.BatchJobEnum;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Ananth Shanmugam
 * Primary class for scheduling jobs using quartz
 */
@Service
@Slf4j
public class QrtzJobScheduler {

	@Autowired
	SchedulerFactoryBean factory;
	//GenerateAccountTransactionScheduledJob
	@SuppressWarnings("rawtypes")
	public boolean scheduler(String jobCode,String exp)  {
		log.debug("Getting a handle to the Scheduler");
		Scheduler scheduler = factory.getScheduler();
		Class className = BatchJobEnum.getJobClass(jobCode);

		
		
		String jobTrigger = jobCode + "Trigger"+ (int )(Math.random() * 50 + 1);
		String jobGroup = className+"_"+ LocalDateTime.now().toString();
		JobKey myJobKey = new JobKey(jobCode, jobGroup);

		CronTrigger trigger1 = (CronTrigger)(newTrigger().withIdentity(jobTrigger, jobGroup)
		    .withDescription("default description").forJob(myJobKey)
		    .withSchedule(CronScheduleBuilder.cronSchedule(exp))
		    .build());

		try {
			scheduler.scheduleJob(jobDetail(className,myJobKey), trigger1);
			log.debug("Starting Scheduler threads");
			scheduler.start();
			return true;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JobDetail jobDetail(Class className,JobKey myJobKey) {
		return newJob().ofType(className).storeDurably().withIdentity(myJobKey)
				.withDescription("Invoke  Job service for"+className.getName()).build();
	}

//	@SuppressWarnings("rawtypes")
//	public Trigger trigger(Class className,String exp,JobKey myJobKey) {
////		String exp = "0 0 0 1/1 * ? *";
//		log.info("Configuring trigger to fire exp {} ", exp);
//		String triggerKey= "Qrtz_Trigger_"+className + "_Trigger_"+ LocalDateTime.now().toString();
//		return newTrigger().forJob(jobDetail(className,myJobKey)).withIdentity(TriggerKey.triggerKey(triggerKey))
//				.withDescription("Trigger for"+className.getName()).withSchedule(CronScheduleBuilder.cronSchedule(exp)).build();
//	}

}
