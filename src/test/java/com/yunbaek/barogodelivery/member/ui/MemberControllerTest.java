package com.yunbaek.barogodelivery.member.ui;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunbaek.barogodelivery.common.exception.DuplicateDataException;
import com.yunbaek.barogodelivery.member.application.MemberService;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import com.yunbaek.barogodelivery.member.dto.MemberResponse;

@DisplayName("사용자는 ")
@Nested
@AutoConfigureRestDocs
@WebMvcTest(controllers = MemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})
@ExtendWith({RestDocumentationExtension.class})
class MemberControllerTest {

    @MockBean
    private MemberService memberService;

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

    @DisplayName("회원 가입을 할 수 있다.")
    @Test
    void createMemberSuccessTest() throws Exception {
        // given
        MemberRequest request = new MemberRequest("loginId", "test", "abcABC!@#123");
        Member member = new Member("loginId", "test", "abcABC!@#123");
        given(memberService.createMember(any(MemberRequest.class)))
                .willReturn(MemberResponse.from(member));

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/members/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapAsString(request)));

        // then
        String body = perform
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).isEqualTo(mapAsString(MemberResponse.from(member)));

        // docs
        perform.andDo(document("members",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                        fieldWithPath("loginId").description("로그인 아이디"),
                        fieldWithPath("name").description("이름"),
                        fieldWithPath("password").description("비밀번호")
                ),
                responseFields(
                        fieldWithPath("id").description("회원 고유번호"),
                        fieldWithPath("loginId").description("로그인 아이디"),
                        fieldWithPath("name").description("이름")
                ))
        );
    }

    @DisplayName("로그인 아이디가 중복되면 회원 가입을 할 수 없다.")
    @Test
    void createMemberFailureWithDuplicateLoginIdTest() throws Exception {
        // given
        MemberRequest request = new MemberRequest("loginId", "test", "abcABC!@#123");
        String errorMessage = "이미 존재하는 아이디입니다. | loginId: loginId";
        given(memberService.createMember(any(MemberRequest.class)))
                .willThrow(new DuplicateDataException(errorMessage));

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/members/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapAsString(request)));

        // then
        String body = perform
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(body).isEqualTo(errorMessage);
    }

    @DisplayName("입력 정보가 올바르지 않으면 회원가입을 할 수 없다.")
    @Test
    void createMemberFailureWithInvalidPasswordLengthTest() throws Exception {
        // given
        MemberRequest request = new MemberRequest("loginId", "test", "invalidPassword");
        given(memberService.createMember(any(MemberRequest.class)))
                .willThrow(new IllegalArgumentException());

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/members/")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL)
                .content(mapAsString(request)));

        // then
        perform
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    private <T> String mapAsString(T request) throws JsonProcessingException {
        return mapper.writeValueAsString(request);
    }
}
