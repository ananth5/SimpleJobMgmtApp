package com.payoneer.jobmgmtservice;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.batch.test.AssertFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;

import com.payoneer.jobmgmtservice.common.constants.BatchJobEnum;
import com.payoneer.jobmgmtservice.job.service.BatchJobLauncher;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ananth Shanmugam Normal test to see whether generate account transaction job executes fine 
 * and whether output file has changed 
 */

@SpringBootTest
public class GenerateAccountTransactionJobTest {

	@Autowired
	private BatchJobLauncher springBatchExecutor;

	
	/*
	 * compare
	 * */
	@Test
	public void testExecution() throws IOException {
		Resource outputResource = new FileSystemResource("output/outputData.csv");
		boolean outputFileExist = outputResource.exists();
		long oldFileLength = 0;
		if (outputFileExist) {
			oldFileLength = outputResource.getFile().length();
		}
		boolean result = springBatchExecutor.launchJob(BatchJobEnum.GENERATE_ACC_TRANSACTION.getJobCode());

		assertTrue(result);
		outputResource = new FileSystemResource("output/outputData.csv"); //old updated file or new file
		if (oldFileLength != 0) {
			//file existed
			assertNotEquals(oldFileLength, outputResource.getFile().length());
		} else {
			//File did not exist previously
			outputFileExist = outputResource.exists();
			assertTrue(outputFileExist);

		}
	}

}
