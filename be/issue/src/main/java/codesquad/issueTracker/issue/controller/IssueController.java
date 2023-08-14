package codesquad.issueTracker.issue.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.global.common.ApiResponse;
import codesquad.issueTracker.issue.dto.IssueWriteRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueContentResponseDto;
import codesquad.issueTracker.issue.dto.ModifyIssueStatusRequestDto;
import codesquad.issueTracker.issue.dto.ModifyIssueTitleRequest;
import codesquad.issueTracker.issue.dto.ModifyIssueTitleResponse;
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
}
