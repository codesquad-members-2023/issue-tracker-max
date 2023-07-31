package com.issuetrackermax.controller.issue;

import org.springframework.web.bind.annotation.RestController;

import com.issuetrackermax.service.issue.IssueService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class IssueController {
	private final IssueService issueService;
}
