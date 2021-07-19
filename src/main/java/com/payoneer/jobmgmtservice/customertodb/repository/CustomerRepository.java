package com.payoneer.jobmgmtservice.customertodb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payoneer.jobmgmtservice.customertodb.domain.Customer;

/**
 * @author Ananth Shanmugam
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

