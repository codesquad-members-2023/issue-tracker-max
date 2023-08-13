package codesquad.issueTracker.issue.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.global.common.ApiResponse;
import codesquad.issueTracker.issue.dto.IssueWriteRequestDto;
import codesquad.issueTracker.issue.service.IssueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class IssueController {

	private final IssueService issueService;

	@PostMapping("/issues")
	public ApiResponse<String> postIssues(@Valid @RequestBody IssueWriteRequestDto request) {
		issueService.save(request);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}
}
