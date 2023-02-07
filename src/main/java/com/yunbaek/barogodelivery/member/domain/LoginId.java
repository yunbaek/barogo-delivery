package com.yunbaek.barogodelivery.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

@Embeddable
public class LoginId {

	@Column(name = "login_id", nullable = false, unique = true)
	private String value;

	protected LoginId() {
	}

	private LoginId(String value) {
		Assert.hasText(value, "로그인 아이디 값은 필수입니다.");
		this.value = value;
	}

	public static LoginId from(String value) {
		return new LoginId(value);
	}
}
