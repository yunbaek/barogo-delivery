package com.yunbaek.barogodelivery.member.ui;

import com.yunbaek.barogodelivery.member.application.MemberService;
import com.yunbaek.barogodelivery.member.dto.MemberRequest;
import com.yunbaek.barogodelivery.member.dto.MemberResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("")
	public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
		MemberResponse member = memberService.createMember(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(member);
	}
}
