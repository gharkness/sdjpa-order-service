package com.gharkness.sdjpaorderservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    private String description;

    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(getDescription(), product.getDescription()) && getProductStatus() == product.getProductStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDescription(), getProductStatus());
    }
}
