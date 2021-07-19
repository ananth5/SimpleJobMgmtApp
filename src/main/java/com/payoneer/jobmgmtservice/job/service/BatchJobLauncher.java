package com.payoneer.jobmgmtservice.job.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payoneer.jobmgmtservice.common.utils.JobFinder;


/**
 * @author Ananth Shanmugam
 */
@Service
public class BatchJobLauncher {


	@Autowired
	private JobLauncher jobLauncher;
	
    @Autowired
    private JobFinder jobFinder;
    
	public boolean launchJob(String jobCode) {
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();
		try {
			Job currJob = jobFinder.getJobBasedOnJobName(jobCode);
			
			jobLauncher.run(currJob, jobParameters);
			return true;
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

			e.printStackTrace();
		}

		return false;
	}
}
