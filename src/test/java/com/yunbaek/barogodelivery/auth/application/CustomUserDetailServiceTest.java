package com.yunbaek.barogodelivery.auth.application;

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
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("security 사용자 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class CustomUserDetailServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CustomUserDetailService customUserDetailService;

    private final String loginId = "loginId";
    private final String name = "name";
    private final String password = "passwordABC!@#123";

    @DisplayName("회원을 검색하여 security가 사용 할 UserDetails를 반환한다.")
    @Test
    void loadUserByUsernameTest() {
        // given
        given(memberRepository.findByLoginId(any(LoginId.class)))
                .willReturn(Optional.of(new Member(loginId, name, password)));

        // when
        UserDetails userDetails = customUserDetailService.loadUserByUsername(loginId);

        // then
        assertThat(userDetails.getUsername()).isEqualTo(loginId);
    }

    @DisplayName("회원을 검색할 수 없으면 예외가 발생한다.")
    @Test
    void loadUserByUsernameFailureTest() {
        // given
        given(memberRepository.findByLoginId(any(LoginId.class)))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> customUserDetailService.loadUserByUsername(loginId))
                .isInstanceOf(AuthorizationException.class)
                .hasMessageContaining("사용자를 찾을 수 없습니다.");
    }

}
