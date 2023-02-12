package com.yunbaek.barogodelivery.delivery.domain;

import io.jsonwebtoken.lang.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Address {

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = true)
    private String detail;

    protected Address() {
    }

    private Address(String zipCode, String state, String city, String street, String detail) {
        Assert.hasText(zipCode, "zipCode must not be null");
        Assert.hasText(state, "state must not be null");
        Assert.hasText(city, "city must not be null");
        Assert.hasText(street, "street must not be null");
        this.zipCode = zipCode;
        this.state = state;
        this.city = city;
        this.street = street;
        this.detail = detail;
    }

    public static Address of(String zipCode, String state, String city, String street, String detail) {
        return new Address(zipCode, state, city, street, detail);
    }

    public String zipCode() {
        return zipCode;
    }

    public String state() {
        return state;
    }

    public String city() {
        return city;
    }

    public String street() {
        return street;
    }

    public String detail() {
        return detail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!Objects.equals(zipCode, address.zipCode)) return false;
        if (!Objects.equals(state, address.state)) return false;
        if (!Objects.equals(city, address.city)) return false;
        if (!Objects.equals(street, address.street)) return false;
        return Objects.equals(detail, address.detail);
    }

    @Override
    public int hashCode() {
        int result = zipCode != null ? zipCode.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (detail != null ? detail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s %s %s %s", zipCode, state, city, street, detail);
    }

}
