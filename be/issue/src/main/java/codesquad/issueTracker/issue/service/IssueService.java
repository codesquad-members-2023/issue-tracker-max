package codesquad.issueTracker.issue.service;

import org.springframework.stereotype.Service;

import codesquad.issueTracker.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {
	private final IssueRepository issueRepository;
}
