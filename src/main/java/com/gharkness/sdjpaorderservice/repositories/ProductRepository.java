package com.gharkness.sdjpaorderservice.repositories;

import com.gharkness.sdjpaorderservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByDescription(String description);
}
