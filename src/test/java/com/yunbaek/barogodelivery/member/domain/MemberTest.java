package com.yunbaek.barogodelivery.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Member Entity 테스트")
class MemberTest {

	@DisplayName("Member 생성 테스트")
	@Test
	void createMemberTest() {
		assertThatNoException()
			.isThrownBy(
				() -> new Member("loginId", "test", "abcABC!@#123")
			);
	}
}
