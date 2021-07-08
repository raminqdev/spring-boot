package com.raminq.jdbctemplate;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
