package com.yunbaek.barogodelivery.member.domain;

import com.yunbaek.barogodelivery.common.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Embedded
	private LoginId loginId;

	@Embedded
	private Name name;

	@Embedded
	private Password password;

	protected Member() {
	}

	public Member(String loginId, String name, String password) {
		this.loginId = LoginId.from(loginId);
		this.name = Name.from(name);
		this.password = Password.from(password);
	}

	public long id() {
		return id;
	}

	public LoginId loginId() {
		return loginId;
	}

	public Password password() {
		return password;
	}

	public Name name() {
		return name;
	}
}
