package codesquard.app.issue.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.issue.dto.request.IssueModifyAssigneesRequest;
import codesquard.app.issue.dto.request.IssueModifyContentRequest;
import codesquard.app.issue.dto.request.IssueModifyLabelsRequest;
import codesquard.app.issue.dto.request.IssueModifyMilestoneRequest;
import codesquard.app.issue.dto.request.IssueModifyStatusRequest;
import codesquard.app.issue.dto.request.IssueModifyTitleRequest;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.dto.response.IssueDeleteResponse;
import codesquard.app.issue.dto.response.IssueModifyResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.dto.response.IssueSaveResponse;
import codesquard.app.issue.service.IssueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/api/issues/{issueId}")
	public ResponseEntity<IssueReadResponse> get(@PathVariable Long issueId) {
		IssueReadResponse issueReadResponse = issueService.get(issueId);
		return ResponseEntity.status(HttpStatus.OK).body(issueReadResponse);
	}

	@PostMapping("/api/issues")
	public ResponseEntity<IssueSaveResponse> save(
		@Valid @RequestBody IssueSaveRequest issueSaveRequest) {
		Long userId = 1L;
		Long id = issueService.save(issueSaveRequest, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(IssueSaveResponse.success(id));
	}

	@PatchMapping("/api/issues/{issueId}/status")
	public ResponseEntity<IssueModifyResponse> modifyStatus(
		@RequestBody IssueModifyStatusRequest issueModifyStatusRequest, @PathVariable Long issueId) {
		issueService.modifyStatus(issueModifyStatusRequest, issueId);
		return ResponseEntity.status(HttpStatus.OK).body(IssueModifyResponse.success());
	}

	@PatchMapping("/api/issues/{issueId}/title")
	public ResponseEntity<IssueModifyResponse> modifyTitle(
		@Valid @RequestBody IssueModifyTitleRequest issueModifyTitleRequest, @PathVariable Long issueId) {
		issueService.modifyTitle(issueModifyTitleRequest, issueId);
		return ResponseEntity.status(HttpStatus.OK).body(IssueModifyResponse.success());
	}

	@PatchMapping("/api/issues/{issueId}/content")
	public ResponseEntity<IssueModifyResponse> modifyContent(
		@Valid @RequestBody IssueModifyContentRequest issueModifyContentRequest, @PathVariable Long issueId) {
		issueService.modifyContent(issueModifyContentRequest, issueId);
		return ResponseEntity.status(HttpStatus.OK).body(IssueModifyResponse.success());
	}

	@PatchMapping("/api/issues/{issueId}/milestones")
	public ResponseEntity<IssueModifyResponse> modifyMilestone(
		@RequestBody IssueModifyMilestoneRequest issueModifyMilestoneRequest, @PathVariable Long issueId) {
		issueService.modifyMilestone(issueModifyMilestoneRequest, issueId);
		return ResponseEntity.status(HttpStatus.OK).body(IssueModifyResponse.success());
	}

	@PatchMapping("/api/issues/{issueId}/assignees")
	public ResponseEntity<IssueModifyResponse> modifyAssignees(
		@RequestBody IssueModifyAssigneesRequest issueModifyAssigneesRequest, @PathVariable Long issueId) {
		issueService.modifyAssignees(issueModifyAssigneesRequest, issueId);
		return ResponseEntity.status(HttpStatus.OK).body(IssueModifyResponse.success());
	}

	@PatchMapping("/api/issues/{issueId}/labels")
	public ResponseEntity<IssueModifyResponse> modifyLabels(
		@RequestBody IssueModifyLabelsRequest issueModifyLabelsRequest, @PathVariable Long issueId) {
		issueService.modifyLabels(issueModifyLabelsRequest, issueId);
		return ResponseEntity.status(HttpStatus.OK).body(IssueModifyResponse.success());
	}

	@DeleteMapping("/api/issues/{issueId}")
	public ResponseEntity<IssueDeleteResponse> delete(@PathVariable Long issueId) {
		issueService.delete(issueId);
		return ResponseEntity.status(HttpStatus.OK).body(IssueDeleteResponse.success());
	}
}
