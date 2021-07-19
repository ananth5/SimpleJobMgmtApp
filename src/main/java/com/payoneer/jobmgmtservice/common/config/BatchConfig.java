package com.payoneer.jobmgmtservice.common.config;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.springframework.batch.core.Job;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

/**
 * @author Ananth Shanmugam 
 */
public interface BatchConfig {

	@SuppressWarnings("rawtypes")
	public ItemReader reader() throws MalformedURLException, FileNotFoundException;

	@SuppressWarnings("rawtypes")
	public ItemProcessor processor();

	@SuppressWarnings("rawtypes")
	public ItemWriter writer();
	
    public abstract Job jobBuilder() throws Exception;

}
