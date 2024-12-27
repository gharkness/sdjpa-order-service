package com.gharkness.sdjpaorderservice.services;

import com.gharkness.sdjpaorderservice.domain.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateQuantityOnHand(Long productId, Integer quantityOnHand);

}
