package com.yunbaek.barogodelivery.auth.adapter;

import com.yunbaek.barogodelivery.auth.domain.LoginMember;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class LoginMemberAdapter extends User {
    private final LoginMember loginMember;

    public LoginMemberAdapter(LoginMember loginMember, String password) {
        super(loginMember.loginId(), password, authorities());
        this.loginMember = loginMember;
    }

    private static Collection<? extends GrantedAuthority> authorities() {
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(() -> "USER");
        return grantedAuthorities;
    }

    public LoginMember getLoginMember() {
        return loginMember;
    }

    public String loginId() {
        return loginMember.loginId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LoginMemberAdapter that = (LoginMemberAdapter) o;

        return Objects.equals(loginMember, that.loginMember);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (loginMember != null ? loginMember.hashCode() : 0);
        return result;
    }
}
