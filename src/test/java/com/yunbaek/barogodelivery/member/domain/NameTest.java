package com.yunbaek.barogodelivery.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

@DisplayName("이름 domain 테스트")
class NameTest {

	@DisplayName("이름 생성 테스트")
	@Test
	void createNameTest() {
		Assertions.assertThatCode(
				() -> Name.from("test"))
			.doesNotThrowAnyException();
	}

	@DisplayName("이름이 null 이거나 비어있는 경우 예외 발생 테스트")
	@ParameterizedTest
	@NullAndEmptySource
	void createNameWithNullNameTest(String name) {
		Assertions.assertThatIllegalArgumentException()
			.isThrownBy(
					() -> Name.from(name)
			);
	}


}