package com.yunbaek.barogodelivery.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yunbaek.barogodelivery.member.MemberRequest;
import com.yunbaek.barogodelivery.member.MemberResponse;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

	@PostMapping
	public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request) {
		return ResponseEntity.ok().build();
	}
}
