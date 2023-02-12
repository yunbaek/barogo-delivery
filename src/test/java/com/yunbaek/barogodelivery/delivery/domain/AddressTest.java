package com.yunbaek.barogodelivery.delivery.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주소 도메인 테스트")
class AddressTest {

    @DisplayName("주소 생성 테스트")
    @Test
    void createAddressTest() {
        assertThatCode(
                () -> Address.of("12345", "서울특별시", "강남구", "역삼동", "123-45")
        ).doesNotThrowAnyException();
    }

    @DisplayName("주소 생성 실패 테스트")
    @ParameterizedTest
    @NullAndEmptySource
    void createAddressFailTest(String input) {
        assertAll(
                () -> assertThatThrownBy(
                        () -> Address.of(input, "서울특별시", "강남구", "역삼동", "123-45")
                ).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(
                        () -> Address.of("12345", input, "강남구", "역삼동", "123-45")
                ).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(
                        () -> Address.of("12345", "서울특별시", input, "역삼동", "123-45")
                ).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(
                        () -> Address.of("12345", "서울특별시", "강남구", input, "123-45")
                ).isInstanceOf(IllegalArgumentException.class)
        );
    }
}
