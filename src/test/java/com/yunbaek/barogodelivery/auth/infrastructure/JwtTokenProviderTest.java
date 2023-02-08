package com.yunbaek.barogodelivery.auth.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JWT 토큰 생성 테스트")
class JwtTokenProviderTest {

    @DisplayName("토큰 생성 테스트")
    @Test
    void createTokenTest() {
        // given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "test");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", 1000L);

        // when
        String token = jwtTokenProvider.createToken("test");

        // then
        assertThat(token).isNotNull();
    }

    @DisplayName("토큰 검증 테스트")
    @Test
    void validateTokenTest() {
        // given
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "test");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", 10000L);
        String token = jwtTokenProvider.createToken("test");

        // when
        boolean isValid = jwtTokenProvider.validateToken(token);

        // then
        assertThat(isValid).isTrue();
    }
}
