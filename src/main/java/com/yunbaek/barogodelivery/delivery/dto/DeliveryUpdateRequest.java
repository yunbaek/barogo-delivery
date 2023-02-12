package com.yunbaek.barogodelivery.delivery.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeliveryUpdateRequest {

    private String zipCode;
    private String state;
    private String city;
    private String street;
    private String detail;

    public DeliveryUpdateRequest() {
    }

    @Builder
    public DeliveryUpdateRequest(String zipCode, String state, String city, String street, String detail) {
        this.zipCode = zipCode;
        this.state = state;
        this.city = city;
        this.street = street;
        this.detail = detail;
    }
}
