package org.presents.issuetracker.issue.controller;

import java.util.List;

import org.presents.issuetracker.issue.dto.IssueDto;
import org.presents.issuetracker.issue.service.IssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IssueController {
	private final IssueService issueService;

	@GetMapping("/")
	public String hello(){
		return "Hello!";
	}

	@GetMapping("/issues")
	public List<IssueDto> issue(){
		return issueService.getIssueList();
	}

}
