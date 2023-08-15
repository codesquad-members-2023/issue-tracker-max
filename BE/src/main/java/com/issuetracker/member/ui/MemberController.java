package com.issuetracker.member.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.issuetracker.member.application.MemberService;
import com.issuetracker.member.ui.dto.MemberResponse;
import com.issuetracker.member.ui.dto.MemberUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> showProfile(@PathVariable Long id) {
		return ResponseEntity.ok()
			.body(MemberResponse.from(memberService.findById(id)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestPart MemberUpdateRequest request,
		@RequestPart(required = false) MultipartFile file) {
		memberService.updateMember(request.toMemberUpdateData(id, file));
		return ResponseEntity.noContent().build();
	}
}
