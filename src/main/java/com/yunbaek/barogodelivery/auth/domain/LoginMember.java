package com.yunbaek.barogodelivery.auth.domain;

import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Name;

public class LoginMember {

	private Long id;
	private LoginId loginId;
	private Name name;

	private LoginMember(Long id, LoginId loginId, Name name) {
		this.id = id;
		this.loginId = loginId;
		this.name = name;
	}

	public static LoginMember of(Long id, LoginId loginId, Name name) {
		return new LoginMember(id, loginId, name);
	}
}
