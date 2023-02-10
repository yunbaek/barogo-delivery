package com.yunbaek.barogodelivery.auth.application;

import com.yunbaek.barogodelivery.auth.adapter.LoginMemberAdapter;
import com.yunbaek.barogodelivery.auth.domain.LoginMember;
import com.yunbaek.barogodelivery.common.exception.AuthorizationException;
import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	public CustomUserDetailService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepository.findByLoginId(LoginId.from(username))
			.orElseThrow(() -> new AuthorizationException("사용자를 찾을 수 없습니다. | id: " + username));

		return new LoginMemberAdapter(LoginMember.from(member), member.password().toString());
	}
}
