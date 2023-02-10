package com.yunbaek.barogodelivery.auth.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        User user = new User("test", "test", new ArrayList<>());
        given(userDetailsService.loadUserByUsername(any())).willReturn(user);
        ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "test");
        ReflectionTestUtils.setField(jwtTokenProvider, "validityInMilliseconds", 1000L);

        // when
//        String token = jwtTokenProvider.createToken(authentication);

        // then
//        assertThat(token).isNotNull();
    }
}
