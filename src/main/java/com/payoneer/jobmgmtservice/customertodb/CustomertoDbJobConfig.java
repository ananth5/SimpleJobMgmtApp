package com.payoneer.jobmgmtservice.customertodb;

import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;

import com.payoneer.jobmgmtservice.common.config.BatchConfig;
import com.payoneer.jobmgmtservice.customertodb.domain.Customer;
import com.payoneer.jobmgmtservice.customertodb.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;


/**
 * @author Ananth Shanmugam 
 * Configuration class for customer TO DB migration job
 */
@Configuration
@EnableBatchProcessing
@Slf4j
public class CustomertoDbJobConfig implements BatchConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Lazy
	public CustomerRepository personRepository;

//	@Autowired
//	protected JobCompletionNotificationListener listener;

    @Value("${csvfile.name}")
    private String fileName;
    
	@SuppressWarnings("rawtypes")
	public ItemReader reader() throws FileNotFoundException  {
		log.info("reader");

		if(!new ClassPathResource(fileName).exists()) {
			System.out.println("path is"+new ClassPathResource(fileName).getPath());
			
			throw new FileNotFoundException("File Not Found");
		}
		ItemReader<Customer> reader = (new FlatFileItemReaderBuilder<Customer>().name("customerItemReader")
				.resource(new ClassPathResource(fileName)).delimited().names(new String[] { "firstName", "lastName" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Customer>() {
					{
						setTargetType(Customer.class);
					}
				}).build());
		
		return reader;
	}

	@SuppressWarnings("rawtypes")
	public ItemWriter writer() {
		RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
		writer.setRepository(personRepository);
		writer.setMethodName("save");
		return writer;
	}

	@SuppressWarnings("rawtypes")
	public ItemProcessor processor() {
		return (ItemProcessor)new CustomerItemProcessor();
	}

	@SuppressWarnings({ "unchecked" })
	public Step step1() throws Exception {
		return this.stepBuilderFactory.get("step1").<Customer, Customer>chunk(5).reader(reader()).processor(processor())
				.writer(writer()).build();
	}

	public Job jobBuilder() throws Exception {
		return this.jobBuilderFactory.get("customerUpdateJob").incrementer(new RunIdIncrementer())
				.start(step1()).build();
	}

}
