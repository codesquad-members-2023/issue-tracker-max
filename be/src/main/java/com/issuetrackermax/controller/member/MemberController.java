package com.issuetrackermax.controller.member;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.member.dto.request.CheckLoginIdRequest;
import com.issuetrackermax.controller.member.dto.request.SignUpRequest;
import com.issuetrackermax.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/signup")
public class MemberController {
	private final MemberService memberService;

	@PostMapping()
	public ApiResponse<Void> signup(
		@RequestBody
		@Valid SignUpRequest signUpRequest) {
		memberService.registerMember(signUpRequest);
		return ApiResponse.success();
	}

	@GetMapping("/check-member-email")
	public ApiResponse<Void> checkLoginId(@ModelAttribute CheckLoginIdRequest request) {
		memberService.checkLoginIdDuplication(request.getLoginId());
		return ApiResponse.success();
	}
}
