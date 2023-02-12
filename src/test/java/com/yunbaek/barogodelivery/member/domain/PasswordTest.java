package com.yunbaek.barogodelivery.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("비밀번호 domain 테스트")
class PasswordTest {

	@DisplayName("비밀번호 생성 테스트")
	@Test
	void createPasswordTest() {
		assertThatCode(
				() -> Password.from("passWORD123!@#")
		).doesNotThrowAnyException();
	}

	@DisplayName("비밀번호가 null 이거나 비어있는 경우 예외 발생 테스트")
	@ParameterizedTest
	@NullAndEmptySource
	void createPasswordWithNullPasswordTest(String password) {
		assertThatIllegalArgumentException()
				.isThrownBy(
						() -> Password.from(password)
				);
	}
}
