package com.payoneer.jobmgmtservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ananth Shanmugam
 * Class to define entry point for spring boot to run
 */

@SpringBootApplication
public class PayoneerAppLauncher {

	public static void main(String[] args) {
		SpringApplication.run(PayoneerAppLauncher.class, args);
	}

}
