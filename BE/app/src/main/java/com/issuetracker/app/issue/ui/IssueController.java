package com.issuetracker.app.issue.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.app.issue.application.IssueService;
import com.issuetracker.app.issue.ui.dto.IssueSearchRequest;
import com.issuetracker.app.issue.ui.dto.IssuesSearchResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class IssueController {

	private final IssueService issueService;

	@GetMapping("/issues")
	public ResponseEntity<IssuesSearchResponse> showIssues(@ModelAttribute IssueSearchRequest issueSearchRequest) {
		IssuesSearchResponse issuesSearchResponse = IssuesSearchResponse.from(issueService.search(issueSearchRequest.toIssueSearchData(1L)));
		return ResponseEntity.ok().body(issuesSearchResponse);
	}
}
