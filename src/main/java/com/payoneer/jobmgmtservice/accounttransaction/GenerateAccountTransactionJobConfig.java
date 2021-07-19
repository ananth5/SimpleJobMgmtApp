package com.payoneer.jobmgmtservice.accounttransaction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.payoneer.jobmgmtservice.accounttransaction.domain.UserTransactionHistory;
import com.payoneer.jobmgmtservice.common.config.BatchConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ananth Shanmugam 
 * Configuration class for generate account transaction job
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class GenerateAccountTransactionJobConfig implements BatchConfig{

	
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

//	@Autowired
//	protected JobCompletionNotificationListener listener;


	private Resource outputResource = new FileSystemResource("output/outputData.csv");

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@SuppressWarnings("rawtypes")
	public ItemReader reader() {
		
		 log.info("in GenerateAccountTransactionJobConfig reader");
		 JpaPagingItemReader<UserTransactionHistory> fullfillment = new JpaPagingItemReader<>();
	        try {
	            String sqlQuery = "SELECT * FROM USERTRANSACTIONHISTORY";
	            JpaNativeQueryProvider<UserTransactionHistory> queryProvider = new JpaNativeQueryProvider<UserTransactionHistory>();
	            queryProvider.setSqlQuery(sqlQuery);
	            queryProvider.setEntityClass(UserTransactionHistory.class);
	            queryProvider.afterPropertiesSet();
	            fullfillment.setEntityManagerFactory(entityManagerFactory);
	            fullfillment.setPageSize(3);
	            fullfillment.setQueryProvider(queryProvider);
	            fullfillment.afterPropertiesSet();
	            fullfillment.setSaveState(true);
	        }
	        catch (Exception e) {
	            log.error("BatchConfiguration.reader() ==> error " + e.getMessage());
	        }
	        return fullfillment;
	}

	private String[] getColumnNames() {
		List<String> list = new ArrayList<String>();
		Field[] fields = UserTransactionHistory.class.getDeclaredFields();
		for(Field field: fields) {
			list.add(field.getName());
		}
		String[] result = new String[list.size()];
		return list.toArray(result);
	}
	
	 @Bean
	 public FlatFileItemWriter<UserTransactionHistory> writer() 
	    {
		 log.info("in GenerateAccountTransactionJobConfig writer");

	        //Create writer instance
	        FlatFileItemWriter<UserTransactionHistory> writer = new FlatFileItemWriter<>();
	         
	        //Set output file location
	        writer.setResource(outputResource);
	         
	        //All job repetitions should "append" to same output file
	        writer.setAppendAllowed(true);
	         
	        //Name field values sequence based on object properties 
	        writer.setLineAggregator(new DelimitedLineAggregator<UserTransactionHistory>() {
	            {
	                setDelimiter(",");
	                setFieldExtractor(new BeanWrapperFieldExtractor<UserTransactionHistory>() {
	                    {
	                        //setNames(new String[] { "TRANSACTIONID", "REFNO", "CHANNEL","USERID","TRANSACTIONTYPE","FROMACCNO","FROMACCTYPE" });
	                    	setNames(getColumnNames());
	                    }
	                });
	            }
	        });
	        return writer;
	    }

	@SuppressWarnings("rawtypes")
	public ItemProcessor processor() {
		return (ItemProcessor)new GenerateAccountTransactionProcessor();
	}

	@SuppressWarnings("unchecked")
	public Step step1() throws Exception {
		
		return this.stepBuilderFactory.get("step1").<UserTransactionHistory, UserTransactionHistory>chunk(5).reader(reader())
				.writer(writer()).build();
	}

	public Job jobBuilder() throws Exception {
		return this.jobBuilderFactory.get("generateAccountTransactionJob").incrementer(new RunIdIncrementer())
				.start(step1()).build();
	}

}
