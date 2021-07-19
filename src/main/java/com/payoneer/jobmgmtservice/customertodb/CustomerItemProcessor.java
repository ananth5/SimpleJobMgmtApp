package com.payoneer.jobmgmtservice.customertodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.payoneer.jobmgmtservice.customertodb.domain.Customer;


/**
 * @author Ananth Shanmugam 
 * Processor file for customer to db migration job
 */

@SuppressWarnings("rawtypes")
public class CustomerItemProcessor implements ItemProcessor{

	private static final Logger log = LoggerFactory.getLogger(CustomerItemProcessor.class);

	public Object process(final Object customer) throws Exception {

		if (customer instanceof Customer) {

			final String firstName = ((Customer) customer).getFirstName().toUpperCase();
			final String lastName = ((Customer) customer).getLastName().toUpperCase();

			final Customer transformedCustomer = new Customer(1L, firstName, lastName);

			log.info("Converting (" + customer + ") into (" + transformedCustomer + ")");
			return transformedCustomer;

		} else {
			return null;
		}
	}

}