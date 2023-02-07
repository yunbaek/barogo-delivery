package com.yunbaek.barogodelivery.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("LoginId domain 테스트")
class LoginIdTest {

	@DisplayName("LoginId 생성 테스트")
	@Test
	void createLoginIdTest() {
		assertThatCode(
				() -> LoginId.from("test")
		).doesNotThrowAnyException();
	}

	@DisplayName("LoginId가 null 이거나 비어있는 경우 예외 발생 테스트")
	@ParameterizedTest
	@NullAndEmptySource
	void createLoginIdWithNullLoginIdTest(String loginId) {
		assertThatIllegalArgumentException()
				.isThrownBy(
						() -> LoginId.from(loginId)
				);
	}

}