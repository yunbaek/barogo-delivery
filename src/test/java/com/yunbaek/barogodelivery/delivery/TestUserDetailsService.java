package com.yunbaek.barogodelivery.delivery;

import com.yunbaek.barogodelivery.auth.adapter.LoginMemberAdapter;
import com.yunbaek.barogodelivery.auth.domain.LoginMember;
import com.yunbaek.barogodelivery.member.domain.Member;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Profile("test")
public class TestUserDetailsService implements UserDetailsService {

    private final Member member;

    public TestUserDetailsService() {
        this.member = new Member("test", "test", "password123!@#");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new LoginMemberAdapter(LoginMember.from(member), member.password().toString());
    }
}
