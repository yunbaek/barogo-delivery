package com.yunbaek.barogodelivery.member.application;

import com.yunbaek.barogodelivery.common.exception.DuplicateDataException;
import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import com.yunbaek.barogodelivery.member.dto.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

	private static final String DUPLICATE_MEMBER_MESSAGE_FORMAT = "이미 존재하는 아이디입니다. | loginId: %s";
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
			throw new DuplicateDataException(String.format(DUPLICATE_MEMBER_MESSAGE_FORMAT, loginId));
		}
	}

	private boolean existsByLoginId(LoginId loginId) {
		return memberRepository.existsByLoginId(loginId);
	}
}
