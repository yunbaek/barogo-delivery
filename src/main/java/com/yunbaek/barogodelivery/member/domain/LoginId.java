package com.yunbaek.barogodelivery.member.domain;

import java.util.Objects;

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

	public String value() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		LoginId loginId = (LoginId)o;

		return Objects.equals(value, loginId.value);
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}

	@Override
	public String toString() {
		return value;
	}
}
