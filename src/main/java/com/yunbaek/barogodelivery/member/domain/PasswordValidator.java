package com.yunbaek.barogodelivery.member.domain;

public class PasswordValidator {


    private static final String MINIMUM_LENGTH = "^[\\s\\S]{12,}$";
    private static final String UPPER_LETTER_REGEX = "[A-Z]";
    private static final String LOWER_LETTER_GEG = "[a-z]";
    private static final String NUMBER_REGEX = "\\d";
    private static final String SPECIAL_CHARACTER_REGEX = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]";
    private static final int MINIMUM_MATCH_NUMBER = 3;

    private static final String INVALID_PASSWORD_MESSAGE = "비밀번호는 소문자, 대문자, 숫자, 특수문자 중 3개 이상이 포함된 12자리 이상의 숫자여야 합니다.";

    private PasswordValidator() {
    }

    private static boolean validateFormat(String password) {
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

    public static void validate(String password) {
        if (!validateFormat(password)) {
            throw new IllegalArgumentException(INVALID_PASSWORD_MESSAGE);
        }
    }
}
