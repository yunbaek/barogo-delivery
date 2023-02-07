package com.yunbaek.barogodelivery.member.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yunbaek.barogodelivery.common.exception.DuplicateDataException;
import com.yunbaek.barogodelivery.member.application.MemberService;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import com.yunbaek.barogodelivery.member.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("사용자는 ")
@Nested
@WebMvcTest(controllers = MemberController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})
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
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
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
                .with(csrf())
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
