package com.yunbaek.barogodelivery.auth.application;

import com.yunbaek.barogodelivery.auth.dto.TokenRequest;
import com.yunbaek.barogodelivery.auth.dto.TokenResponse;
import com.yunbaek.barogodelivery.auth.infrastructure.JwtTokenProvider;
import com.yunbaek.barogodelivery.common.exception.AuthorizationException;
import com.yunbaek.barogodelivery.member.domain.LoginId;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@DisplayName("인증 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;
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
        given(memberRepository.findByLoginId(any(LoginId.class)))
                .willReturn(Optional.of(new Member(loginId, name, password)));
        given(jwtTokenProvider.createToken(anyString())).willReturn("TOKEN");
        TokenRequest request = new TokenRequest(loginId, password);

        // when
        TokenResponse token = authService.login(request);

        // then
        assertThat(token.getAccessToken()).isNotBlank();
    }

    @DisplayName("로그인 아이디로 회원을 찾을 수 없으면 예외가 발생한다.")
    @Test
    void loginFailureWithNullTest() {
        // given
        given(memberRepository.findByLoginId(any(LoginId.class)))
                .willReturn(Optional.empty());
        TokenRequest request = new TokenRequest(loginId, password);

        // when & then
        assertThatThrownBy(
                () -> authService.login(request)
        ).isInstanceOf(AuthorizationException.class)
                .hasMessageContaining("가입되지 않은 아이디입니다.");
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void loginFailureWithWrongPasswordTest() {
        // given
        given(memberRepository.findByLoginId(any(LoginId.class)))
                .willReturn(Optional.of(new Member(loginId, name, password)));
        TokenRequest request = new TokenRequest(loginId, password + "wrong");

        // when & then
        assertThatThrownBy(
                () -> authService.login(request)
        ).isInstanceOf(AuthorizationException.class)
                .hasMessageContaining("비밀번호가 일치하지 않습니다.");

    }

}
