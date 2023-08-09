package org.presents.issuetracker.issue.controller;

import java.util.List;

import org.presents.issuetracker.global.dto.response.IdResponseDto;
import org.presents.issuetracker.issue.dto.request.IssueCreateRequest;
import org.presents.issuetracker.issue.dto.request.IssueSearchParam;
import org.presents.issuetracker.issue.dto.request.IssueUpdateRequest;
import org.presents.issuetracker.issue.dto.response.IssueDetailResponse;
import org.presents.issuetracker.issue.dto.response.IssueSearchResponse;
import org.presents.issuetracker.issue.service.IssueService;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<IdResponseDto> create(@RequestBody IssueCreateRequest issueCreateRequest) {
		IdResponseDto issueCreateResponse = IdResponseDto.builder()
			.id(issueService.create(issueCreateRequest))
			.build();

		return ResponseEntity.status(HttpStatus.OK).body(issueCreateResponse);
	}

	/*
		이슈 목록 조회(메인 페이지)
		1. GET("/issues") 이면 status="open"인 이슈 반환 -> 이슈 목록 조회(메인 페이지)
		2. GET("/issues?query=") 이면 status="open"인 이슈, status="closed"인 이슈 모두 반환 -> filter 삭제 버튼 클릭 시
		3. GET("/issues?query=status:closed") 이면 status="closed"인 이슈 반환 -> filter에 조건 값을 넣었을 시
	 */
	@GetMapping
	public ResponseEntity<IssueSearchResponse> showIssues(@RequestParam(required = false) String query) {
		IssueSearchParam issueSearchParam = IssueSearchParam.from(query);
		return ResponseEntity.ok().body(issueService.getIssues(issueSearchParam));
	}

	@GetMapping("/{issueId}")
	public ResponseEntity<IssueDetailResponse> getIssueDetail(@PathVariable Long issueId) {
		return ResponseEntity.ok().body(issueService.getIssueDetail(issueId));
	}

	@PatchMapping
	public ResponseEntity<IdResponseDto> update(@RequestBody IssueUpdateRequest issueUpdateRequest) {
		IdResponseDto idResponseDto = IdResponseDto.builder()
			.id(issueService.update(issueUpdateRequest))
			.build();

		return ResponseEntity.ok().body(idResponseDto);
	}

	@PutMapping("/{issueId}/labels")
	public ResponseEntity<List<LabelPreviewResponse>> updateLabels(@PathVariable Long issueId,
		@RequestBody List<Long> labelIds) {
		return ResponseEntity.ok().body(issueService.updateLabels(labelIds, issueId));
	}
}
