package com.gharkness.sdjpaorderservice.repositories;

import com.gharkness.sdjpaorderservice.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
