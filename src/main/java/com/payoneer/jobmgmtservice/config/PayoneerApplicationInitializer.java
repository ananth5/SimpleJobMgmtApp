package com.payoneer.jobmgmtservice.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.payoneer.jobmgmtservice.PayoneerAppLauncher;

/**
 * @author Ananth Shanmugam
 * Class to define configuration for spring boot
 */
@SpringBootApplication
public class PayoneerApplicationInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PayoneerAppLauncher.class);
    }

    @Bean(name = "bCryptPasswordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {		/* password encoder instance for spring security */
        return new BCryptPasswordEncoder();
    }
}
