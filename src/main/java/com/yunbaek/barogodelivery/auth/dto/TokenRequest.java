package com.yunbaek.barogodelivery.auth.dto;

import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Password;

public class TokenRequest {

	private String loginId;

	private String password;

	public TokenRequest() {
	}

	public TokenRequest(String loginId, String password) {
		this.loginId = loginId;
		this.password = password;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getPassword() {
		return password;
	}

	public LoginId loginId() {
		return LoginId.from(loginId);
	}

	public Password password() {
		return Password.from(password);
	}
}
