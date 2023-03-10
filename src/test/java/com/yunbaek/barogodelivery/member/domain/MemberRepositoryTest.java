package com.yunbaek.barogodelivery.member.domain;

import com.yunbaek.barogodelivery.config.JpaAuditConfig;
import com.yunbaek.barogodelivery.config.QueryDslConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({JpaAuditConfig.class, QueryDslConfig.class})
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("Member 생성 테스트 - Audit 적용")
	@Test
	void createMemberTest() {
		// given
		Member member = new Member("loginId", "test", "abcABC!@#123");

		// when
		Member savedMember = memberRepository.save(member);

		// then
		assertThat(savedMember).isNotNull();
		assertThat(savedMember.getCreatedDate()).isNotNull();
		assertThat(savedMember.getLastModifiedDate()).isNotNull();
	}

	@DisplayName("loginId 중복 체크 테스트")
	@Test
	void findMemberByLoginIdTest() {
		// given
		Member member = new Member("loginId", "test", "abcABC!@#123");
		memberRepository.save(member);

		// when
		boolean isDuplicated = memberRepository.existsByLoginId(LoginId.from("loginId"));

		// then
		assertThat(isDuplicated).isTrue();
	}
}
