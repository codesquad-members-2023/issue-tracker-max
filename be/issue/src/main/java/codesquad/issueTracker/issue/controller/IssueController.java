package codesquad.issueTracker.issue.controller;

import static codesquad.issueTracker.global.exception.SuccessCode.*;

import codesquad.issueTracker.issue.dto.IssueLabelResponseDto;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
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
	public ApiResponse<String> postIssues(@Valid @RequestBody IssueWriteRequestDto request,
										  HttpServletRequest httpServletRequest) {
		Long id = Long.parseLong(String.valueOf(httpServletRequest.getAttribute("userId")));
		issueService.save(request, id);
		return ApiResponse.success(SUCCESS.getStatus(), SUCCESS.getMessage());
	}

	@GetMapping("/issues/labels")
	public ApiResponse<List<IssueLabelResponseDto>> getIssueLabels() {
		List<IssueLabelResponseDto> labels = issueService.getIssueLabels();
		return ApiResponse.success(SUCCESS.getStatus(), labels);
	}
}
