package com.yunbaek.barogodelivery.member.ui;

import static com.yunbaek.barogodelivery.utils.RestAssertUtils.*;
import static org.assertj.core.api.Assertions.*;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yunbaek.barogodelivery.common.exception.DuplicateDataException;
import com.yunbaek.barogodelivery.member.domain.Member;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import com.yunbaek.barogodelivery.member.dto.MemberResponse;
import com.yunbaek.barogodelivery.utils.ControllerTest;

@DisplayName("사용자는 ")
@Nested
class MemberControllerTest extends ControllerTest {

    private static final String MEMBERS_URI = "/api/v1/members/";

    @DisplayName("회원 가입을 할 수 있다.")
    @Test
    void createMemberSuccessTest() throws Exception {
        // given
        MemberRequest request = new MemberRequest("loginId", "test", "abcABC!@#123");
        MemberResponse response = MemberResponse.from(new Member("loginId", "test", "abcABC!@#123"));
        given(memberService.createMember(any(MemberRequest.class))).willReturn(response);

        // when
        ResultActions perform = doPost(mockMvc, MEMBERS_URI, request);

        // then
        perform.andExpect(status().isCreated());
        String body = extractResponse(perform);
        assertThat(body).isEqualTo(mapAsString(response));

        // docs
        createRestDocs(perform);
    }

    private void createRestDocs(ResultActions perform) throws Exception {
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
        ResultActions perform = doPost(mockMvc, MEMBERS_URI, request);

        // then
        perform.andExpect(status().isBadRequest());
        String body = extractResponse(perform);
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
        ResultActions perform = doPost(mockMvc, MEMBERS_URI, request);

        // then
        perform.andExpect(status().isBadRequest());
    }

    private <T> String mapAsString(T request) throws JsonProcessingException {
        return mapper.writeValueAsString(request);
    }
}
