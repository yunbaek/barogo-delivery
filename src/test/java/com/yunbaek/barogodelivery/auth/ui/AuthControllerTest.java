package com.yunbaek.barogodelivery.auth.ui;

import static com.yunbaek.barogodelivery.utils.RestAssertUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.yunbaek.barogodelivery.auth.dto.TokenRequest;
import com.yunbaek.barogodelivery.auth.dto.TokenResponse;
import com.yunbaek.barogodelivery.utils.ControllerTest;

@DisplayName("사용자는 ")
@Nested
class AuthControllerTest extends ControllerTest {

    @DisplayName("로그인을 할 수 있다.")
    @Test
    void loginTest() throws Exception {
        // given
        TokenRequest request = new TokenRequest("user", "passwordABC123!@#");
        TokenResponse response = new TokenResponse("accessToken");
        given(authService.login(any())).willReturn(response);

        // when
        ResultActions perform = doPost(mockMvc, "/login/token/", request);

        // then
        perform.andExpect(status().isOk());
        String responseBody = extractResponse(perform);
        assertThat(responseBody).isEqualTo(mapper.writeValueAsString(response));

        // docs
        createRestDocs(perform);
    }

    private void createRestDocs(ResultActions perform) throws Exception {
        perform.andDo(document("auth",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("loginId").description("사용자 아이디"),
                        fieldWithPath("password").description("사용자 비밀번호")
                ),
                responseFields(
                        fieldWithPath("accessToken").description("액세스 토큰")
                )
        ));
    }
}
