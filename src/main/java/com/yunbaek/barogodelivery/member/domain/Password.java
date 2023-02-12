package com.yunbaek.barogodelivery.member.domain;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Password {

	@Column(name = "password", nullable = false)
	private String value;

	protected Password() {
	}

	private Password(String value) {
		Assert.hasText(value, "password must not be null");
		this.value = value;
	}

	public static Password from(String value) {
		return new Password(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Password password = (Password)o;

		return Objects.equals(value, password.value);
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
