package codesquard.app.issue.controller;

import org.springframework.web.bind.annotation.RestController;

import codesquard.app.issue.service.IssueService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class IssueController {

	private final IssueService issueService;
}
