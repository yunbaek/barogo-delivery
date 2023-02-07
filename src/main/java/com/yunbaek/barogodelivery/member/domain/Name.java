package com.yunbaek.barogodelivery.member.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

@Embeddable
public class Name {

	@Column(name = "name", nullable = false)
	private String value;

	protected Name() {
	}

	private Name(String value) {
		Assert.hasText(value, "이름 값은 필수입니다.");
		this.value = value;
	}

	public static Name from(String value) {
		return new Name(value);
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

		Name name = (Name)o;

		return Objects.equals(value, name.value);
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
