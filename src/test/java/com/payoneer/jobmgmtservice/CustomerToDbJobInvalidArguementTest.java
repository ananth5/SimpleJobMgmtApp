package com.payoneer.jobmgmtservice;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.payoneer.jobmgmtservice.common.constants.BatchJobEnum;
import com.payoneer.jobmgmtservice.job.service.BatchJobLauncher;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Ananth Shanmugam
 * This is to test an invalid arguement to the job where the file name is incorrect.
 */
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CustomerToDbJobInvalidArguementTest {

    @Autowired
    private BatchJobLauncher springBatchExecutor;
    
//    @Autowired
//    private BookRepository bookRepository;

    @Test
    public void testExecution() {
        
    	IllegalArgumentException thrown = assertThrows(
    			IllegalArgumentException.class,
    	           () -> springBatchExecutor.launchJob(BatchJobEnum.POPULATE_CUSTOMER_DETAILS_TO_DB.getJobCode()),
    	           "The Job must not be null"
    	    );

   	    assertTrue(thrown.getMessage().contains("The Job must not be null"));
    	
    } 

}
