package com.payoneer.jobmgmtservice.common.constants;

import com.payoneer.jobmgmtservice.accounttransaction.GenerateAccountTransactionScheduledJob;
import com.payoneer.jobmgmtservice.customertodb.CustomerToDbScheduledJob;

/**
 * @author Ananth Shanmugam 
 * Important file to specify batch jobs supported by the job management app
 */
public enum BatchJobEnum {

	POPULATE_CUSTOMER_DETAILS_TO_DB("populateCustomerDetailsToDb",CustomerToDbScheduledJob.class),
	GENERATE_ACC_TRANSACTION("generateAccTrxn",GenerateAccountTransactionScheduledJob.class),
    ;

    private String batchJobCode;
    
    @SuppressWarnings("rawtypes")
	private Class className;

    @SuppressWarnings("rawtypes")
	BatchJobEnum(String batchJobCode,Class className) {
        this.batchJobCode = batchJobCode;
        this.className = className;
    }

    public static BatchJobEnum getJobCode(String code) {
        for (BatchJobEnum currCode : BatchJobEnum.values()) {
            if ( currCode.getJobCode().equals(code) )
                return currCode;
        }
        return null;
    }

    @SuppressWarnings("rawtypes")
	public static Class getJobClass(String code) {
        for (BatchJobEnum currCode : BatchJobEnum.values()) {
            if ( currCode.getJobCode().equals(code) )
                return currCode.getClassName();
        }
        return null;
    }
    @SuppressWarnings("rawtypes")
	public Class getClassName() {
    	return className;
    }
    
    public void setClassName(@SuppressWarnings("rawtypes") Class className) {
    	this.className = className;
    }

    public String getJobCode() {
        return batchJobCode;
    }

    public void setJobCode(String jobCode) {
        this.batchJobCode = jobCode;
    }
}
