package org.presents.issuetracker.issue.controller;

import org.presents.issuetracker.global.dto.response.IdResponseDto;
import org.presents.issuetracker.issue.dto.request.IssueCreateRequestDto;
import org.presents.issuetracker.issue.dto.response.IssueSearchResponse;
import org.presents.issuetracker.issue.service.IssueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
public class IssueController {
	private final IssueService issueService;

	@PostMapping("/new")
	public ResponseEntity<IdResponseDto> create(@RequestBody IssueCreateRequestDto issueCreateRequestDto) {
		IdResponseDto issueCreateResponse = IdResponseDto.builder()
			.id(issueService.create(issueCreateRequestDto))
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(issueCreateResponse);
	}

	@GetMapping() // query가 없을 시 null
	public ResponseEntity<IssueSearchResponse> showIssues(@RequestParam(required = false) String query) {
		return ResponseEntity.ok().body(issueService.getIssues());
	}
}
