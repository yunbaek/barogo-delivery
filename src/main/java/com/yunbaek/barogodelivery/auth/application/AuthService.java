package com.yunbaek.barogodelivery.auth.application;

import com.yunbaek.barogodelivery.auth.dto.TokenRequest;
import com.yunbaek.barogodelivery.auth.dto.TokenResponse;
import com.yunbaek.barogodelivery.auth.infrastructure.JwtTokenProvider;
import com.yunbaek.barogodelivery.common.exception.AuthorizationException;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	private static final String MEMBER_NOT_FOUND_ERROR_MESSAGE_FORMAT = "가입되지 않은 아이디입니다. | id: %s";
	private static final String PASSWORD_NOT_MATCH_ERROR_MESSAGE = "비밀번호가 일치하지 않습니다.";
	private final MemberRepository memberRepository;

	private final JwtTokenProvider jwtTokenProvider;

	public AuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
		this.memberRepository = memberRepository;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	public TokenResponse login(TokenRequest request) {
		Member member = memberRepository.findByLoginId(request.loginId())
			.orElseThrow(
				() -> new AuthorizationException(String.format(MEMBER_NOT_FOUND_ERROR_MESSAGE_FORMAT, request.getLoginId())));
		matchPassword(request.getPassword(), member);

		String token = jwtTokenProvider.createToken(request.getLoginId());
		return new TokenResponse(token);
	}

	private void matchPassword(String password, Member member) {
		if(!member.matchPassword(password)) {
			throw new AuthorizationException(PASSWORD_NOT_MATCH_ERROR_MESSAGE);
		}
	}
}
