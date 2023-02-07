package com.yunbaek.barogodelivery.member.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.yunbaek.barogodelivery.config.JpaAuditConfig;

@DataJpaTest
@Import(JpaAuditConfig.class)
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("Member 생성 테스트 - Audit 적용")
	@Test
	void createMemberTest() {
		Member member = new Member("loginId", "test", "abcABC!@#123");
		Member savedMember = memberRepository.save(member);

		assertThat(savedMember.getCreatedDate()).isNotNull();
		assertThat(savedMember.getLastModifiedDate()).isNotNull();
	}

}