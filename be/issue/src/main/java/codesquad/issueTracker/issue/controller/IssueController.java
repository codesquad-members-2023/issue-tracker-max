package codesquad.issueTracker.issue.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquad.issueTracker.issue.service.IssueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class IssueController {
	private final IssueService issueService;
}
