package com.yunbaek.barogodelivery.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import com.yunbaek.barogodelivery.member.dto.MemberResponse;

@Service
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public MemberResponse createMember(MemberRequest request) {
		validateDuplicateLoginId(loginId(request));
		Member member = memberRepository.save(request.toMember());
		return MemberResponse.of(member);
	}

	private LoginId loginId(MemberRequest request) {
		return LoginId.from(request.getLoginId());
	}

	private void validateDuplicateLoginId(LoginId loginId) {
		if (existsByLoginId(loginId)) {
			throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
		}
	}

	private boolean existsByLoginId(LoginId loginId) {
		return memberRepository.existsByLoginId(loginId);
	}
}
