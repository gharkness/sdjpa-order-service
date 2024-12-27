package com.gharkness.sdjpaorderservice.services;

import com.gharkness.sdjpaorderservice.domain.Product;
import com.gharkness.sdjpaorderservice.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    @Transactional
    @Override
    public Product updateQuantityOnHand(Long productId, Integer quantityOnHand) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        product.setQuantityOnHand(quantityOnHand);

        return productRepository.saveAndFlush(product);
    }
}
