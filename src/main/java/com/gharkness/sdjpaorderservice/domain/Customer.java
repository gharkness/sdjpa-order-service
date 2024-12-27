package com.gharkness.sdjpaorderservice.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Customer extends BaseEntity {

    @Length(max = 50)
    private String customerName;

    @Valid
    @Embedded
    private Address address;

    @Length(max = 20)
    private String phone;

    @Email
    private String email;

    @Version
    private Integer version;

    @OneToMany(mappedBy = "customer")
    private Set<OrderHeader> orders = new LinkedHashSet<>();
}
