package org.presents.issuetracker.issue.controller;

import java.util.List;

import org.presents.issuetracker.global.dto.response.IdResponseDto;
import org.presents.issuetracker.issue.dto.request.IssueCreateRequest;
import org.presents.issuetracker.issue.dto.request.IssueSearchParam;
import org.presents.issuetracker.issue.dto.request.IssueStatusUpdateRequest;
import org.presents.issuetracker.issue.dto.request.IssueUpdateRequest;
import org.presents.issuetracker.issue.dto.response.IssueDetailResponse;
import org.presents.issuetracker.issue.dto.response.IssueSearchResponse;
import org.presents.issuetracker.issue.service.IssueService;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.user.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

		return ResponseEntity.status(HttpStatus.CREATED).body(issueCreateResponse);
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

	@PatchMapping("/title")
	public ResponseEntity<IdResponseDto> updateTitle(@RequestBody IssueUpdateRequest issueUpdateRequest) {
		IdResponseDto idResponseDto = IdResponseDto.builder()
			.id(issueService.updateTitle(issueUpdateRequest))
			.build();

		return ResponseEntity.ok().body(idResponseDto);
	}

	@PatchMapping("/contents")
	public ResponseEntity<IdResponseDto> updateContents(@RequestBody IssueUpdateRequest issueUpdateRequest) {
		IdResponseDto idResponseDto = IdResponseDto.builder()
			.id(issueService.updateContents(issueUpdateRequest))
			.build();

		return ResponseEntity.ok().body(idResponseDto);
	}

	@PutMapping("/{issueId}/labels")
	public ResponseEntity<List<LabelPreviewResponse>> updateLabels(@PathVariable Long issueId,
		@RequestBody List<Long> labelIds) {
		return ResponseEntity.ok().body(issueService.updateLabels(labelIds, issueId));
	}

	@PutMapping("/{issueId}/assignees")
	public ResponseEntity<List<UserResponse>> updateAssignees(@PathVariable Long issueId,
		@RequestBody List<Long> assigneeIds) {
		return ResponseEntity.ok().body(issueService.updateAssignees(assigneeIds, issueId));
	}

	@PutMapping("/{issueId}/milestone")
	public ResponseEntity<MilestonePreviewResponse> updateMilestone(@PathVariable Long issueId,
		@RequestBody Long milestoneId) {
		return ResponseEntity.ok().body(issueService.updateMilestone(milestoneId, issueId));
	}

	@PutMapping("/status")
	public ResponseEntity<Void> updateStatus(@RequestBody IssueStatusUpdateRequest issueStatusUpdateRequest) {
		issueService.updateStatus(issueStatusUpdateRequest.getIssueIds(), issueStatusUpdateRequest.getStatus());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{issueId}")
	public ResponseEntity<Void> delete(@PathVariable Long issueId) {
		issueService.delete(issueId);
		return ResponseEntity.noContent().build();
	}
}
