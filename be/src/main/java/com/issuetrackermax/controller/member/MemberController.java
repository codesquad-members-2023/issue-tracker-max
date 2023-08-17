package com.issuetrackermax.controller.member;

import java.util.List;

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
import com.issuetrackermax.controller.member.dto.response.MemberContentResponse;
import com.issuetrackermax.service.member.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/signup")
	public ApiResponse<Void> signup(
		@RequestBody
		@Valid SignUpRequest signUpRequest) {
		memberService.registerMember(signUpRequest);
		return ApiResponse.success();
	}

	@GetMapping("/signup/check-member-email")
	public ApiResponse<Void> checkLoginId(@ModelAttribute CheckLoginIdRequest request) {
		memberService.checkLoginIdDuplication(request.getLoginId());
		return ApiResponse.success();
	}

	@GetMapping("/show-content")
	public ApiResponse<List<MemberContentResponse>> memberList() {
		return ApiResponse.success(memberService.getMemberList());
	}
}
