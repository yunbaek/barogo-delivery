package com.yunbaek.barogodelivery.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	private PasswordEncoder() {
	}

	public static String encode(String password) {
		return encoder.encode(password);
	}

	public static boolean matches(String rawPassword, String encodedPassword) {
		return encoder.matches(rawPassword, encodedPassword);
	}

}
