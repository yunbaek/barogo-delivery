package com.yunbaek.barogodelivery.member.dto;

import com.yunbaek.barogodelivery.member.domain.Member;

import lombok.Data;

@Data
public class MemberRequest {

	private String loginId;
	private String name;
	private String password;

	public MemberRequest(String loginId, String name, String password) {
		this.loginId = loginId;
		this.name = name;
		this.password = password;
	}

	public Member toMember() {
		return new Member(loginId, name, password);
	}
}
