package com.yunbaek.barogodelivery.member.dto;

import com.yunbaek.barogodelivery.member.domain.Member;

public class MemberResponse {

	private Long id;
	private String loginId;
	private String name;

	private MemberResponse(Long id, String loginId, String name) {
		this.id = id;
		this.loginId = loginId;
		this.name = name;
	}
	public static MemberResponse from(Member member) {
		return new MemberResponse(member.id(), member.loginId().value(), member.name().value());
	}

	public long getId() {
		return id;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getName() {
		return name;
	}
}
