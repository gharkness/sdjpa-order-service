package com.gharkness.sdjpaorderservice.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {

    private String address;

    @Length(max = 30)
    private String city;

    private String state;

    private String zipCode;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address1 = (Address) o;
        return Objects.equals(getAddress(), address1.getAddress()) && Objects.equals(getCity(), address1.getCity()) && Objects.equals(getState(), address1.getState()) && Objects.equals(getZipCode(), address1.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAddress(), getCity(), getState(), getZipCode());
    }
}
