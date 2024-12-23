package com.gharkness.sdjpaorderservice.repositories;

import com.gharkness.sdjpaorderservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByDescription(String description);
}
