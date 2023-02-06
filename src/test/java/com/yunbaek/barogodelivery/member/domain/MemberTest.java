package com.yunbaek.barogodelivery.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Member Entity 테스트")
class MemberTest {

    @DisplayName("Member 생성 테스트")
    @Test
    void createMemberTest() {
        assertDoesNotThrow(() ->
                new Member("test", "test"));
    }

}
