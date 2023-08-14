package codesquad.issueTracker.issue.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import codesquad.issueTracker.global.common.ApiResponse;
import codesquad.issueTracker.issue.dto.IssueFileResponseDto;
import codesquad.issueTracker.issue.dto.IssueLabelResponseDto;
import codesquad.issueTracker.issue.dto.IssueMilestoneResponseDto;
import codesquad.issueTracker.issue.dto.IssueOptionResponseDto;
import codesquad.issueTracker.issue.dto.IssueResponseDto;
import codesquad.issueTracker.issue.dto.IssueUserResponseDto;
import codesquad.issueTracker.issue.dto.IssueWriteRequestDto;
import codesquad.issueTracker.issue.dto.ModifyAssigneeRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentResponseDto;
import codesquad.issueTracker.issue.dto.ModifyIssueMilestoneDto;
import codesquad.issueTracker.issue.dto.ModifyIssueStatusRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueTitleRequest;
import codesquad.issueTracker.issue.dto.ModifyIssueTitleResponse;
import codesquad.issueTracker.issue.dto.ModifyLabelRequestDto;
import codesquad.issueTracker.issue.service.IssueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class IssueController {

	private final IssueService issueService;

	@PostMapping("/issues")
	public ApiResponse<String> postIssues(@Valid @RequestBody IssueWriteRequestDto request,
		HttpServletRequest httpServletRequest) {
		Long id = Long.parseLong(String.valueOf(httpServletRequest.getAttribute("userId")));
		issueService.save(request, id);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@GetMapping("/issues/labels")
	public ApiResponse<IssueLabelResponseDto> getIssueLabels() {
		IssueLabelResponseDto labels = issueService.getIssueLabels();
		return ApiResponse.success(SUCCESS.getStatus(), labels);
	}

	@GetMapping("/issues/milestones")
	public ApiResponse<IssueMilestoneResponseDto> getIssueMilestones() {
		IssueMilestoneResponseDto milestones = issueService.getIssueMilestones();
		return ApiResponse.success(SUCCESS.getStatus(), milestones);
	}

	@GetMapping("/issues/participants")
	public ApiResponse<IssueUserResponseDto> getIssueUsers() {
		IssueUserResponseDto participants = issueService.getIssueUsers();
		return ApiResponse.success(SUCCESS.getStatus(), participants);
	}

	@GetMapping("/issues/{issueId}")
	public ApiResponse<IssueResponseDto> getIssue(@PathVariable Long issueId) {
		IssueResponseDto issueResponseDto = issueService.getIssueById(issueId);
		return ApiResponse.success(SUCCESS.getStatus(), issueResponseDto);
	}

	@GetMapping("/issues/{issueId}/options")
	public ApiResponse<IssueOptionResponseDto> getIssueOptions(@PathVariable Long issueId) {
		IssueOptionResponseDto issueOptionResponseDto = issueService.getIssueOptions(issueId);
		return ApiResponse.success(SUCCESS.getStatus(), issueOptionResponseDto);
	}

	@PatchMapping("/issues/status")
	public ApiResponse<String> patchStatus(@RequestBody ModifyIssueStatusRequestDto request) {
		issueService.modifyIssueStatus(request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PatchMapping("/issues/{id}/status")
	public ApiResponse<String> patchInDetailStatus(@PathVariable Long id,
		@RequestBody ModifyIssueStatusRequestDto request) {
		issueService.modifyIssueStatusInDetail(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PatchMapping("/issues/{id}/content")
	public ApiResponse<ModifyIssueContentResponseDto> patchContent(@PathVariable Long id,
		@RequestBody ModifyIssueContentRequestDto request) {
		ModifyIssueContentResponseDto response = issueService.modifyIssueContent(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), response);
	}

	@PatchMapping("/issues/{id}/title")
	public ApiResponse<ModifyIssueTitleResponse> patchTitle(@PathVariable Long id,
		@Valid @RequestBody ModifyIssueTitleRequest request) {
		ModifyIssueTitleResponse response = issueService.modifyIssueTitle(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), response);
	}

	@DeleteMapping("/issues/{id}")
	public ApiResponse<String> deleteIssues(@PathVariable Long id) {
		issueService.delete(id);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PatchMapping("/issues/{id}/assignees")
	public ApiResponse<String> patchAssignees(@PathVariable Long id, @RequestBody ModifyAssigneeRequestDto request) {
		issueService.modifyAssignees(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PatchMapping("/issues/{id}/labels")
	public ApiResponse<String> patchLabels(@PathVariable Long id, @RequestBody ModifyLabelRequestDto request) {
		issueService.modifyLabels(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PatchMapping("/issues/{id}/milestones")
	public ApiResponse<String> patchMilestone(@PathVariable Long id, @RequestBody ModifyIssueMilestoneDto request) {
		issueService.modifyMilestone(id, request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@PostMapping("/upload")
	public ApiResponse<IssueFileResponseDto> uploadFile(@RequestPart(value = "file") MultipartFile multipartFile) {
		IssueFileResponseDto response = issueService.uploadImg(multipartFile);
		return ApiResponse.success(SUCCESS.getStatus(), response);
	}
}
