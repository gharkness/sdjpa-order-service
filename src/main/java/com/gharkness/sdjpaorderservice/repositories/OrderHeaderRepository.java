package com.gharkness.sdjpaorderservice.repositories;

import com.gharkness.sdjpaorderservice.domain.Customer;
import com.gharkness.sdjpaorderservice.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
    List<OrderHeader> findAllByCustomer(Customer customer);
}
