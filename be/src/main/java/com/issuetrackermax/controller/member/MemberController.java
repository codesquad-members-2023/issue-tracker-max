package com.issuetrackermax.controller.member;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.service.Member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/signup")
	public ApiResponse<Void> signup(@RequestBody SignUpRequest signUpRequest) {
		memberService.registerMember(signUpRequest);
		return ApiResponse.success();
	}
	
}
