package com.yunbaek.barogodelivery.member.application;

import com.yunbaek.barogodelivery.common.exception.DuplicateDataException;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("회원 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private MemberService memberService;

	@DisplayName("회원을 생성할 수 있다.")
	@Test
	void createMemberTest() {
		// given
		String loginId = "loginId";
		String test = "test";
		String password = "abcABC!@#123";
		MemberRequest request = new MemberRequest(loginId, test, password);
		given(memberRepository.save(any())).willReturn(new Member(loginId, test, password));
		given(passwordEncoder.encode(password)).willReturn(password);

		// when
		memberService.createMember(request);

		// then
		verify(memberRepository, times(1)).save(any());
		verify(memberRepository, times(1)).existsByLoginId(any());
	}

	@DisplayName("중복된 아이디로 회원 생성 시도 시 예외가 발생한다.")
	@Test
	void createMemberWithDuplicateLoginIdTest() {
		// given
		String loginId = "loginId";
		String test = "test";
		String password = "abcABC!@#123";
		MemberRequest request = new MemberRequest(loginId, test, password);
		given(memberRepository.existsByLoginId(any())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> memberService.createMember(request))
				.isInstanceOf(DuplicateDataException.class)
						.hasMessageContaining("이미 존재하는 아이디입니다.");
	}

}
