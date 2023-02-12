package com.yunbaek.barogodelivery.delivery.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("배달 도메인 테스트")
class DeliveryTest {

    private Address 출발지_주소;
    private Address 도착지_주소;

    @BeforeEach
    void setUp() {
        출발지_주소 = Address.of("12345", "서울특별시", "강남구", "역삼동", "123-45");
        도착지_주소 = Address.of("12345", "서울특별시", "강남구", "삼성동", "111-22");
    }

    @Test
    @DisplayName("배달 생성 테스트")
    void createDeliveryTest() {
        Assertions.assertThatCode(
                () -> Delivery.of(
                        1L,
                        1L,
                        1L,
                        출발지_주소,
                        도착지_주소
                )
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("배달 초기 상태 테스트")
    void deliveryStatusTest() {
        Delivery delivery = Delivery.of(
                1L,
                1L,
                1L,
                출발지_주소,
                도착지_주소
        );

        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.ORDERED);
    }

    @Test
    @DisplayName("배달 상태 변경 가능 테스트")
    void changeDeliveryStatusTest() {
        Delivery delivery = Delivery.of(
                1L,
                1L,
                1L,
                출발지_주소,
                도착지_주소
        );

        assertThat(delivery.enableUpdateStatus()).isTrue();
    }

    @Test
    @DisplayName("배달 상태 변경 테스트")
    void updateDeliveryStatusTest() {
        Delivery delivery = Delivery.of(
                1L,
                1L,
                1L,
                출발지_주소,
                도착지_주소
        );

        delivery.updateStatus(DeliveryStatus.DISPATCHED);

        assertThat(delivery.getStatus()).isEqualTo(DeliveryStatus.DISPATCHED);
    }

    @ParameterizedTest
    @DisplayName("배달 상태가 배달 중이거나, 배달 완료, 취소 일 때 변경 불가 테스트")
    @MethodSource
    void updateDeliveryStatusFailTest(DeliveryStatus status) {
        Delivery delivery = Delivery.of(
                1L,
                1L,
                1L,
                출발지_주소,
                도착지_주소
        );

        delivery.updateStatus(status);

        assertThat(delivery.enableUpdateStatus()).isFalse();
    }

    static DeliveryStatus[] updateDeliveryStatusFailTest() {
        return new DeliveryStatus[]{
                DeliveryStatus.DELIVERING,
                DeliveryStatus.DELIVERED,
                DeliveryStatus.CANCELLED
        };
    }

}
