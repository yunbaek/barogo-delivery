package com.yunbaek.barogodelivery.delivery.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("배달 상태 테스트")
class DeliveryStatusTest {

    @DisplayName("배달중 이전 상태 테스트")
    @Test
    void isBeforeDeliveringTest() {
        assertAll(
                () -> assertThat(DeliveryStatus.ORDERED.isBeforeDelivering()).isTrue(),
                () -> assertThat(DeliveryStatus.DISPATCHED.isBeforeDelivering()).isTrue(),
                () -> assertThat(DeliveryStatus.PICKED_UP.isBeforeDelivering()).isTrue(),
                () -> assertThat(DeliveryStatus.DELIVERING.isBeforeDelivering()).isFalse(),
                () -> assertThat(DeliveryStatus.DELIVERED.isBeforeDelivering()).isFalse(),
                () -> assertThat(DeliveryStatus.CANCELLED.isBeforeDelivering()).isFalse()
        );
    }

}
