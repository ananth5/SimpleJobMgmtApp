package com.payoneer.jobmgmtservice.common.utils;

import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.payoneer.jobmgmtservice.accounttransaction.GenerateAccountTransactionJobConfig;
import com.payoneer.jobmgmtservice.common.constants.BatchJobEnum;
import com.payoneer.jobmgmtservice.customertodb.CustomertoDbJobConfig;

@Component
public class JobFinder {
	
	@Autowired
	private CustomertoDbJobConfig customertoDbJobConfig;

	@Autowired
	private GenerateAccountTransactionJobConfig generateAccountTransactionJobConfig;
	
	 public Job getJobBasedOnJobName(String jobName) {
	    	
	    	if(jobName.equals(BatchJobEnum.POPULATE_CUSTOMER_DETAILS_TO_DB.getJobCode())) {
	    		try {
					return customertoDbJobConfig.jobBuilder();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}else if(jobName.equals(BatchJobEnum.GENERATE_ACC_TRANSACTION.getJobCode())){
	    		try {
					return generateAccountTransactionJobConfig.jobBuilder();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    		return null;
	    }
}
