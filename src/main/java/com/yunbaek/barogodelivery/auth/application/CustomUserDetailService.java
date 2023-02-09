package com.yunbaek.barogodelivery.auth.application;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yunbaek.barogodelivery.common.exception.AuthorizationException;
import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	public CustomUserDetailService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	// TODO 테스트 작성
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginId(LoginId.from(username))
			.orElseThrow(() -> new AuthorizationException("사용자를 찾을 수 없습니다. | id: " + username));

		return User
			.withUsername(username)
			.password(member.password().toString())
			.authorities("USER")
			.build();
	}
}
