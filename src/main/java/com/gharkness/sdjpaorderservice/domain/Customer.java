package com.gharkness.sdjpaorderservice.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

    @Size(max = 50)
    private String customerName;

    @Valid
    @Embedded
    private Address address;

    @Size(max = 20)
    private String phone;

    @Email
    @Size(max = 255)
    private String email;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "customer")
    private Set<OrderHeader> orders = new LinkedHashSet<>();
}
