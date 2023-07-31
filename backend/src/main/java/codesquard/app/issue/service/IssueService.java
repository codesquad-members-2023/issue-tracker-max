package codesquard.app.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.issue.dto.request.IssueRegisterRequest;
import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;

	@Transactional
	public Long register(IssueRegisterRequest issueRegisterRequest, Long userId) {
		Long id = issueRepository.save(issueRegisterRequest.toEntity(userId));
		registerIssueLabel(id, issueRegisterRequest.getLabels());
		registerIssueAssignee(id, issueRegisterRequest.getAssignees());
		return id;
	}

	private void registerIssueLabel(Long issueId, List<Long> labels) {
		for (Long labelId : labels) {
			issueRepository.saveIssueLabel(issueId, labelId);
		}
	}

	private void registerIssueAssignee(Long issueId, List<Long> assignees) {
		for (Long assignee : assignees) {
			issueRepository.saveIssueAssignee(issueId, assignee);
		}
	}
}
