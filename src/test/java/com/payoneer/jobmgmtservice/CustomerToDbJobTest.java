package com.payoneer.jobmgmtservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.payoneer.jobmgmtservice.common.constants.BatchJobEnum;
import com.payoneer.jobmgmtservice.customertodb.repository.CustomerRepository;
import com.payoneer.jobmgmtservice.job.service.BatchJobLauncher;


/**
 * @author Ananth Shanmugam
 * Normal test to see whether the customer repo has the records after the job
 */
@SpringBootTest
public class CustomerToDbJobTest {

    @Autowired
    private BatchJobLauncher springBatchExecutor;
    
    @Autowired
    CustomerRepository customerRepository;
    
    @Test
    public void testExecution() {
        long initialCount = customerRepository.findAll().size();
        assertEquals(0L, initialCount);
        boolean result = springBatchExecutor.launchJob(BatchJobEnum.POPULATE_CUSTOMER_DETAILS_TO_DB.getJobCode());
        long count = customerRepository.findAll().size();
        assertEquals(1L, count);
        assertTrue(result);
    } 

}
