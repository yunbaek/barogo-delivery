package com.yunbaek.barogodelivery.auth.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

@DisplayName("JWT 토큰 생성 테스트")
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("토큰 생성 테스트")
    @Test
    void createTokenTest() {
        // given
        Authentication mock = mock(Authentication.class);
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "test");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", 1000L);

        // when
        String token = jwtTokenProvider.createToken(mock);

        // then
        assertThat(token).isNotNull();
    }
}
