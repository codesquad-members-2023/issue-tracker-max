package com.issuetrackermax.controller.issue;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.controller.ApiResponse;
import com.issuetrackermax.controller.issue.dto.request.IssueApplyRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuePostRequest;
import com.issuetrackermax.controller.issue.dto.request.IssueTitleRequest;
import com.issuetrackermax.controller.issue.dto.request.IssuesStatusRequest;
import com.issuetrackermax.controller.issue.dto.response.IssueDetailsResponse;
import com.issuetrackermax.controller.issue.dto.response.IssuePostResponse;
import com.issuetrackermax.service.issue.IssueService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController {
	private final IssueService issueService;

	@PostMapping
	public ApiResponse<IssuePostResponse> post(@RequestBody IssuePostRequest request) {
		return ApiResponse.success(IssuePostResponse.from(issueService.post(request)));
	}

	@PatchMapping("/status")
	public ApiResponse<Void> updateStatus(@RequestBody IssuesStatusRequest request) {
		issueService.updateStatus(request);
		return ApiResponse.success();
	}

	@GetMapping("/{id}")
	public ApiResponse<IssueDetailsResponse> show(@PathVariable Long id) {
		return ApiResponse.success(issueService.show(id));
	}

	@DeleteMapping("/{id}")
	public ApiResponse<Void> delete(@PathVariable Long id) {
		issueService.delete(id);
		return ApiResponse.success();
	}

	@PatchMapping("/{id}/title")
	public ApiResponse<Void> modifyTitle(@PathVariable Long id, @RequestBody IssueTitleRequest request) {
		issueService.modifyTitle(id, request);
		return ApiResponse.success();
	}

	@PatchMapping("/{id}/labels")
	public ApiResponse<Void> applyLabels(@PathVariable Long id, @RequestBody IssueApplyRequest request) {
		issueService.applyLabels(id, request);
		return ApiResponse.success();
	}

	@PatchMapping("/{id}/assignees")
	public ApiResponse<Void> applyAssignees(@PathVariable Long id, @RequestBody IssueApplyRequest request) {
		issueService.applyAssignees(id, request);
		return ApiResponse.success();
	}

	@PatchMapping("/{issueId}/milestones/{milestoneId}")
	public ApiResponse<Void> applyMilestone(@PathVariable Long issueId, @PathVariable Long milestoneId) {
		issueService.applyMilestone(issueId, milestoneId);
		return ApiResponse.success();
	}

}
