package com.gharkness.sdjpaorderservice.repositories;

import com.gharkness.sdjpaorderservice.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
