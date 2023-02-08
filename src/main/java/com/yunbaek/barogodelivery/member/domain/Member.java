package com.yunbaek.barogodelivery.member.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.yunbaek.barogodelivery.common.domain.BaseEntity;

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

	public String loginId() {
		return loginId.value();
	}

	public String name() {
		return name.value();
	}

	public boolean matchPassword(String password) {
		return this.password.match(password);
	}
}
