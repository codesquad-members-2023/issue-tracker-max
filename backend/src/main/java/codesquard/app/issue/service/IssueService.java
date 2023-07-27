package codesquard.app.issue.service;

import org.springframework.stereotype.Service;

import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;
}
