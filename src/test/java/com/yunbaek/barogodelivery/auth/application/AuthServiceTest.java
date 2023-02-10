package com.yunbaek.barogodelivery.auth.application;

import com.yunbaek.barogodelivery.auth.dto.TokenRequest;
import com.yunbaek.barogodelivery.auth.dto.TokenResponse;
import com.yunbaek.barogodelivery.auth.infrastructure.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("인증 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    String loginId = "loginId";
    String name = "name";
    String password = "passwordABC!@#123";

    @DisplayName("로그인이 성공한다.")
    @Test
    void loginSuccessTest() {
        // given
        given(jwtTokenProvider.createToken(any())).willReturn("TOKEN");
        TokenRequest request = new TokenRequest(loginId, password);

        // when
        TokenResponse token = authService.login(request);

        // then
        assertThat(token.getAccessToken()).isNotBlank();
    }

    @DisplayName("로그인이 성공한다.")
    @Test
    void loginSuccessTeste() {
        // given
        given(authenticationManager.authenticate(any())).willReturn(null);
        TokenRequest request = new TokenRequest(loginId, password);

        // when
        TokenResponse token = authService.login(request);

        // then
        assertThat(token.getAccessToken()).isNotBlank();
    }
}
