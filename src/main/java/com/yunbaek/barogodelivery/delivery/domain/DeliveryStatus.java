package com.yunbaek.barogodelivery.delivery.domain;

public enum DeliveryStatus {
    ORDERED,
    DISPATCHED,
    PICKED_UP,
    DELIVERING,
    DELIVERED,
    CANCELLED;

    public boolean isBeforeDelivering() {
        return this == ORDERED || this == DISPATCHED || this == PICKED_UP;
    }
}
