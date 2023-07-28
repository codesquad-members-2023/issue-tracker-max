package codesquard.app.issue.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.issue.dto.request.IssueRegisterRequest;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;

	@Transactional
	public Long register(IssueRegisterRequest issueRegisterRequest, Long userId) {
		Issue issue = Issue.builder()
			.userId(userId)
			.title(issueRegisterRequest.getTitle())
			.content(issueRegisterRequest.getContent())
			.milestoneId(issueRegisterRequest.getMilestone())
			.build();
		Long id = issueRepository.save(issue);
		for (Long labelId : issueRegisterRequest.getLabels()) {
			issueRepository.saveIssueLabel(id, labelId);
		}
		for (Long assignee : issueRegisterRequest.getAssignees()) {
			issueRepository.saveIssueAssignee(id, assignee);
		}
		return id;
	}
}
