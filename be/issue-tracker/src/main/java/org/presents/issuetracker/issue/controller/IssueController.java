package org.presents.issuetracker.issue.controller;

import org.presents.issuetracker.issue.dto.response.IssueListResponseDto;
import org.presents.issuetracker.issue.service.IssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IssueController {
	private final IssueService issueService;

	@GetMapping("/issues") // query가 없을 시 null
	public IssueListResponseDto issue(@RequestParam(required = false) String query) {
		return issueService.getIssueList();
	}
}
