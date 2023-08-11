package codesquard.app.issue.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import codesquard.app.api.response.ApiResponse;
import codesquard.app.api.response.ResponseMessage;
import codesquard.app.authenticate_user.entity.AuthenticateUser;
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
import codesquard.app.issue.mapper.request.IssueFilterRequest;
import codesquard.app.issue.mapper.response.IssueFilterResponse;
import codesquard.app.issue.service.IssueQueryService;
import codesquard.app.issue.service.IssueService;
import codesquard.app.user.annotation.Login;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(path = "/api/issues")
@RestController
public class IssueController {

	private final IssueService issueService;
	private final IssueQueryService issueQueryService;

	// TODO: 유효하지 않은 값이 들어왔을 경우 빈 리스트 반환
	@GetMapping()
	public ApiResponse<IssueFilterResponse> listIssues(@ModelAttribute IssueFilterRequest request,
		@Login AuthenticateUser user) {
		return ApiResponse.ok(issueQueryService.findFilterIssues(user.toEntity().getLoginId(), request));
	}

	@GetMapping("/{issueId}")
	public ApiResponse<IssueReadResponse> get(@PathVariable Long issueId) {
		return ApiResponse.ok(issueQueryService.get(issueId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ApiResponse<IssueSaveResponse> save(
		@Valid @RequestBody IssueSaveRequest issueSaveRequest) {
		Long userId = 1L;
		Long issueId = issueService.save(issueSaveRequest, userId);
		return ApiResponse.of(HttpStatus.CREATED, ResponseMessage.ISSUE_SAVE_SUCCESS,
			IssueSaveResponse.success(issueId));
	}

	@PatchMapping("/{issueId}/status")
	public ApiResponse<IssueModifyResponse> modifyStatus(
		@RequestBody IssueModifyStatusRequest issueModifyStatusRequest, @PathVariable Long issueId) {
		issueService.modifyStatus(issueModifyStatusRequest, issueId);
		return ApiResponse.ok(IssueModifyResponse.success(issueId));
	}

	@PatchMapping("/{issueId}/title")
	public ApiResponse<IssueModifyResponse> modifyTitle(
		@Valid @RequestBody IssueModifyTitleRequest issueModifyTitleRequest, @PathVariable Long issueId) {
		issueService.modifyTitle(issueModifyTitleRequest, issueId);
		return ApiResponse.ok(IssueModifyResponse.success(issueId));
	}

	@PatchMapping("/{issueId}/content")
	public ApiResponse<IssueModifyResponse> modifyContent(
		@Valid @RequestBody IssueModifyContentRequest issueModifyContentRequest, @PathVariable Long issueId) {
		issueService.modifyContent(issueModifyContentRequest, issueId);
		return ApiResponse.ok(IssueModifyResponse.success(issueId));
	}

	@PatchMapping("/{issueId}/milestones")
	public ApiResponse<IssueModifyResponse> modifyMilestone(
		@RequestBody IssueModifyMilestoneRequest issueModifyMilestoneRequest, @PathVariable Long issueId) {
		issueService.modifyMilestone(issueModifyMilestoneRequest, issueId);
		return ApiResponse.ok(IssueModifyResponse.success(issueId));
	}

	@PatchMapping("/{issueId}/assignees")
	public ApiResponse<IssueModifyResponse> modifyAssignees(
		@RequestBody IssueModifyAssigneesRequest issueModifyAssigneesRequest, @PathVariable Long issueId) {
		issueService.modifyAssignees(issueModifyAssigneesRequest, issueId);
		return ApiResponse.ok(IssueModifyResponse.success(issueId));
	}

	@PatchMapping("/{issueId}/labels")
	public ApiResponse<IssueModifyResponse> modifyLabels(
		@RequestBody IssueModifyLabelsRequest issueModifyLabelsRequest, @PathVariable Long issueId) {
		issueService.modifyLabels(issueModifyLabelsRequest, issueId);
		return ApiResponse.ok(IssueModifyResponse.success(issueId));
	}

	@DeleteMapping("/{issueId}")
	public ApiResponse<IssueDeleteResponse> delete(@PathVariable Long issueId) {
		issueService.delete(issueId);
		return ApiResponse.ok(IssueDeleteResponse.success(issueId));
	}

}
