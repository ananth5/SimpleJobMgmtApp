package com.payoneer.jobmgmtservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.payoneer.jobmgmtservice.domain.User;

/**
 * @author Ananth Shanmugam
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // find user by email
    User findByEmail(String email);
}
