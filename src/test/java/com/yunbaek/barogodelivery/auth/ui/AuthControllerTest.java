package com.yunbaek.barogodelivery.auth.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunbaek.barogodelivery.auth.application.AuthService;
import com.yunbaek.barogodelivery.auth.dto.TokenRequest;
import com.yunbaek.barogodelivery.auth.dto.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("사용자는 ")
@Nested
@AutoConfigureRestDocs
@WebMvcTest(controllers = AuthController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})
@ExtendWith({RestDocumentationExtension.class})
class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeEach()
    public void setup(RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터
                .build();
    }

    @DisplayName("로그인을 할 수 있다.")
    @Test
    void loginTest() throws Exception {
        // given
        TokenRequest request = new TokenRequest("user", "passwordABC123!@#");
        TokenResponse response = new TokenResponse("accessToken");
        given(authService.login(any())).willReturn(response);

        // when
        ResultActions perform = mockMvc.perform(post("/login/token/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // then
        String responseBody = perform
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(responseBody).isEqualTo(mapper.writeValueAsString(response));

        // docs
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
