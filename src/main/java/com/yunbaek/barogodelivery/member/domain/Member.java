package com.yunbaek.barogodelivery.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.util.Assert;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	private String password;

	protected Member() {
	}

	public Member(String name, String password) {
		Assert.hasText(name, "name must not be null");
		Assert.hasText(password, "password must not be null");
		Assert.isTrue(isPasswordFormat(password), String.format("%s 비밀번호 형식이 잘못되었습니다.", password));
		this.name = name;
		this.password = password;
	}

	private boolean isPasswordFormat(String password) {
		String minMaxLength = "^[\\s\\S]{12,}$";
		String upper = "[A-Z]";
		String lower = "[a-z]";
		String number = "\\d";
		String special = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]";
		int count = 0;

		if (password.matches(minMaxLength)) {
			// Only need 3 out of 4 of these to match
			if (password.matches(".*" + upper + ".*"))
				count++;
			if (password.matches(".*" + lower + ".*"))
				count++;
			if (password.matches(".*" + number + ".*"))
				count++;
			if (password.matches(".*" + special + ".*"))
				count++;
		}

		return count >= 3;
	}
}
