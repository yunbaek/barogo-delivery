package com.yunbaek.barogodelivery.delivery.dto;

import com.yunbaek.barogodelivery.delivery.domain.DeliveryStatus;
import lombok.Getter;

@Getter
public class DeliveryResponse {

    private long deliveryId;
    private long memberId;
    private long riderId;
    private DeliveryStatus deliveryStatus;
    private String arrivalAddress;
    private String departureAddress;

    public DeliveryResponse(
            long deliveryId,
            long memberId,
            long riderId,
            DeliveryStatus deliveryStatus,
            String arrivalZipCode,
            String arrivalState,
            String arrivalCity,
            String arrivalStreet,
            String arrivalDetail,
            String departureZipCode,
            String departureState,
            String departureCity,
            String departureStreet,
            String departureDetail
    ) {
        this.deliveryId = deliveryId;
        this.memberId = memberId;
        this.riderId = riderId;
        this.deliveryStatus = deliveryStatus;
        this.arrivalAddress = String.format("(%s) %s %s %s %s", arrivalZipCode, arrivalState, arrivalCity, arrivalStreet, arrivalDetail);
        this.departureAddress = String.format("(%s) %s %s %s %s", departureZipCode, departureState, departureCity, departureStreet, departureDetail);
    }

}
