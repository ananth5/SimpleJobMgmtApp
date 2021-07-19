package com.payoneer.jobmgmtservice.accounttransaction;

import org.springframework.batch.item.ItemProcessor;


/**
 * @author Ananth Shanmugam 
 * Processor class for generate account transaction job which is empty for now since there
 * is no processing
 */
@SuppressWarnings("rawtypes")
public class GenerateAccountTransactionProcessor implements ItemProcessor {

	
	public Object process(final Object object) throws Exception {
		return null;
	}

}