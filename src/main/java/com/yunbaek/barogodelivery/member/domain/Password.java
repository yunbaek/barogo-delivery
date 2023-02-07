package com.yunbaek.barogodelivery.member.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

@Embeddable
public class Password {

	private static final String MINIMUM_LENGTH = "^[\\s\\S]{12,}$";
	private static final String UPPER_LETTER_REGEX = "[A-Z]";
	private static final String LOWER_LETTER_GEG = "[a-z]";
	private static final String NUMBER_REGEX = "\\d";
	private static final String SPECIAL_CHARACTER_REGEX = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]";
	private static final int MINIMUM_MATCH_NUMBER = 3;
	private static final String INVALID_PASSWORD_MESSAGE = "비밀번호 형식이 잘못되었습니다.";

	@Column(name = "password", nullable = false)
	private String value;

	protected Password() {
	}

	private Password(String value) {
		Assert.hasText(value, "password must not be null");
		Assert.isTrue(isPasswordFormat(value), INVALID_PASSWORD_MESSAGE);
		this.value = value;
	}

	public static Password from(String value) {
		return new Password(value);
	}

	private boolean isPasswordFormat(String password) {
		int count = 0;
		if (password.matches(MINIMUM_LENGTH)) {
			// Only need 3 out of 4 of these to match
			if (password.matches(".*" + UPPER_LETTER_REGEX + ".*")) {
				count++;
			}
			if (password.matches(".*" + LOWER_LETTER_GEG + ".*")) {
				count++;
			}
			if (password.matches(".*" + NUMBER_REGEX + ".*")) {
				count++;
			}
			if (password.matches(".*" + SPECIAL_CHARACTER_REGEX + ".*")) {
				count++;
			}
		}
		return count >= MINIMUM_MATCH_NUMBER;
	}

	public boolean notEquals(Password password) {
		return !this.value.equals(password.value);
	}

	@Override
	public String toString() {
		return value;
	}
}
