package com.issuetracker.app.issue.ui;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.issuetracker.app.issue.application.IssueService;
import com.issuetracker.app.issue.ui.dto.IssueCreateRequest;
import com.issuetracker.app.issue.ui.dto.IssueCreateResponse;
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

	@PostMapping("/issues")
	public ResponseEntity<IssueCreateResponse> createIssue(@RequestBody IssueCreateRequest issueCreateRequest) {
		IssueCreateResponse issueCreateResponse = IssueCreateResponse.from(issueService.create(issueCreateRequest.toIssueCreateData()));
		return ResponseEntity.created(URI.create("/issues/" + issueCreateResponse.getId()))
			.body(issueCreateResponse);
	}
}
