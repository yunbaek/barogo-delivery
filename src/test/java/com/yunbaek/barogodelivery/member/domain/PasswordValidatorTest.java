package com.yunbaek.barogodelivery.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("비밀번호 유효성 검증 테스트")
class PasswordValidatorTest {


    @DisplayName("비밀번호가 12자리 미만이면 예외 발생 테스트")
    @ParameterizedTest(name = "[{index}] {0} => {1}")
    @MethodSource
    void createMemberWithInvalidPasswordLengthTest(String description, String password) {
        assertThatThrownBy(() -> PasswordValidator.validate(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 소문자, 대문자, 숫자, 특수문자 중 3개 이상이 포함된 12자리 이상의 숫자여야 합니다.");
    }

    private static Stream<Arguments> createMemberWithInvalidPasswordLengthTest() {
        return Stream.of(
                Arguments.of("12자 미만의 비밀번호(문자열 검증조건 충족)", "12abAB!@"),
                Arguments.of("12자 미만의 비밀번호(문자열 검증조건 미충족)", "12abAB"),
                Arguments.of("12자 미만의 비밀번호(문자열 검증조건 미충족)", "1234567890")
        );
    }

    @DisplayName("비밀번호가 소문자, 대문자, 숫자, 특수문자 중 3개 이상이 포함된 12자리 이상의 숫자가 아니면 예외 발생 테스트")
    @ParameterizedTest(name = "[{index}] {0} => {1}")
    @MethodSource
    void createMemberWithInvalidPasswordTest(String description, String password) {
        assertThatThrownBy(() -> PasswordValidator.validate(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 소문자, 대문자, 숫자, 특수문자 중 3개 이상이 포함된 12자리 이상의 숫자여야 합니다.");
    }

    private static Stream<Arguments> createMemberWithInvalidPasswordTest() {
        return Stream.of(
                Arguments.of("소문자만으로 이루어진 12자 이상의 비밀번호", "aaaaaaaaaaaa"),
                Arguments.of("대문자만으로 이루어진 12자 이상의 비밀번호", "AAAAAAAAAAAA"),
                Arguments.of("숫자만으로 이루어진 12자 이상의 비밀번호", "123456789012"),
                Arguments.of("특수문자만으로 이루어진 12자 이상의 비밀번호", "!!!!!!!!!!!!"),
                Arguments.of("소문자와 대문자만으로 이루어진 12자 이상의 비밀번호", "aAaAaAaAaAaA"),
                Arguments.of("소문자와 숫자만으로 이루어진 12자 이상의 비밀번호", "a1a1a1a1a1a1"),
                Arguments.of("소문자와 특수문자만으로 이루어진 12자 이상의 비밀번호", "a!a!a!a!a!a!"),
                Arguments.of("대문자와 숫자만으로 이루어진 12자 이상의 비밀번호", "A1A1A1A1A1A1"),
                Arguments.of("대문자와 특수문자만으로 이루어진 12자 이상의 비밀번호", "A!A!A!A!A!A!"),
                Arguments.of("숫자와 특수문자만으로 이루어진 12자 이상의 비밀번호", "1!1!1!1!1!1!")
        );
    }

    @DisplayName("비밀번호가 소문자, 대문자, 숫자, 특수문자 중 3개 이상이 포함된 12자리 이상의 숫자이면 예외 발생하지 않음 테스트")
    @ParameterizedTest(name = "[{index}] {0} => {1}")
    @MethodSource
    void createMemberWithValidPasswordTest(String description, String password) {
        assertThatCode(() -> PasswordValidator.validate(password)).doesNotThrowAnyException();
    }

    private static Stream<Arguments> createMemberWithValidPasswordTest() {
        return Stream.of(
                Arguments.of("소문자, 대문자, 숫자, 특수문자 모두 포함", "aA1!aA1!aA1!"),
                Arguments.of("소문자, 대문자, 숫자 포함", "aA1aA1aA1aA1"),
                Arguments.of("소문자, 대문자, 특수문자 포함", "aA!aA!aA!aA!"),
                Arguments.of("소문자, 숫자, 특수문자 포함", "a1!a1!a1!a1!"),
                Arguments.of("대문자, 숫자, 특수문자 포함", "A1!A1!A1!A1!"
                ));
    }

}
