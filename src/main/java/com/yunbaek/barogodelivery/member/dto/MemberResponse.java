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
	public static MemberResponse of(Member member) {
		return new MemberResponse(member.id(), member.loginId(), member.name());
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
