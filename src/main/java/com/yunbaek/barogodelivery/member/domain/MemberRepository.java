package com.yunbaek.barogodelivery.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByLoginId(LoginId loginId);

	Optional<Member> findByLoginId(LoginId loginId);
}
