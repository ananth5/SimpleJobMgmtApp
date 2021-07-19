package com.payoneer.jobmgmtservice.accounttransaction;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ananth Shanmugam 
 * Main entry class to schedule job for generate account transaction job
 */
@Component
public class GenerateAccountTransactionScheduledJob implements Job {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private GenerateAccountTransactionJobConfig generateAccountTransactionJobConfig;

	public void execute(JobExecutionContext context) throws JobExecutionException {

		logger.info("Scheduled Job ** {} ** fired @ {}", context.getJobDetail().getKey().getName(),
				context.getFireTime());
		JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
				.toJobParameters();
		org.springframework.batch.core.Job currJob;
		try {
			currJob = generateAccountTransactionJobConfig.jobBuilder();
			jobLauncher.run(currJob, jobParameters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Next job scheduled @ {}", context.getNextFireTime());
	}
}
