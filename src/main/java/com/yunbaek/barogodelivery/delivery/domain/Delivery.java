package com.yunbaek.barogodelivery.delivery.domain;

import com.yunbaek.barogodelivery.common.domain.BaseEntity;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long riderId;

    private long orderId;

    private long memberId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "arrival_zip_code"))
    @AttributeOverride(name = "state", column = @Column(name = "arrival_state"))
    @AttributeOverride(name = "city", column = @Column(name = "arrival_city"))
    @AttributeOverride(name = "street", column = @Column(name = "arrival_street"))
    @AttributeOverride(name = "detail", column = @Column(name = "arrival_detail"))
    private Address arrivalAddress;

    @Embedded
    @AttributeOverride(name = "zipCode", column = @Column(name = "departure_zip_code"))
    @AttributeOverride(name = "state", column = @Column(name = "departure_state"))
    @AttributeOverride(name = "city", column = @Column(name = "departure_city"))
    @AttributeOverride(name = "street", column = @Column(name = "departure_street"))
    @AttributeOverride(name = "detail", column = @Column(name = "departure_detail"))
    private Address departureAddress;


    protected Delivery() {
    }

    private Delivery(long riderId, long orderId, long memberId, Address arrivalAddress, Address departureAddress) {
        this.riderId = riderId;
        this.orderId = orderId;
        this.memberId = memberId;
        this.arrivalAddress = arrivalAddress;
        this.departureAddress = departureAddress;
        this.status = DeliveryStatus.ORDERED;
    }

    public static Delivery of(long riderId, long orderId, long memberId, Address arrivalLocation, Address departureLocation) {
        return new Delivery(riderId, orderId, memberId, arrivalLocation, departureLocation);
    }

    public void updateStatus(DeliveryStatus status) {
        Assert.notNull(status, "DeliveryStatus must not be null");
        this.status = status;
    }

    public boolean enableUpdate() {
        return status.isBeforeDelivering();
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        return id == delivery.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", riderId=" + riderId +
                ", orderId=" + orderId +
                ", memberId=" + memberId +
                ", status=" + status +
                ", arrivalLocation=" + arrivalAddress +
                ", departureLocation=" + departureAddress +
                '}';
    }
}
