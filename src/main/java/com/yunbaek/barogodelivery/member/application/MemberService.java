package com.yunbaek.barogodelivery.member.application;

import com.yunbaek.barogodelivery.common.exception.DuplicateDataException;
import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import com.yunbaek.barogodelivery.member.dto.MemberResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

	private static final String DUPLICATE_MEMBER_MESSAGE_FORMAT = "이미 존재하는 아이디입니다. | loginId: %s";
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public MemberResponse createMember(MemberRequest request) {
		validateDuplicateLoginId(loginId(request));
		request.encodePassword(passwordEncoder);
		Member member = memberRepository.save(request.toMember());
		return MemberResponse.from(member);
	}

	private LoginId loginId(MemberRequest request) {
		return LoginId.from(request.getLoginId());
	}

	private void validateDuplicateLoginId(LoginId loginId) {
		if (existsByLoginId(loginId)) {
			throw new DuplicateDataException(String.format(DUPLICATE_MEMBER_MESSAGE_FORMAT, loginId));
		}
	}

	private boolean existsByLoginId(LoginId loginId) {
		return memberRepository.existsByLoginId(loginId);
	}
}
