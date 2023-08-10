package codesquard.app.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.api.errors.exception.NoSuchIssueException;
import codesquard.app.issue.dto.request.IssueFilterRequest;
import codesquard.app.issue.dto.response.IssueCommentsResponse;
import codesquard.app.issue.dto.response.IssueFilterResponse;
import codesquard.app.issue.dto.response.IssueLabelResponse;
import codesquard.app.issue.dto.response.IssueMilestoneCountResponse;
import codesquard.app.issue.dto.response.IssueMilestoneResponse;
import codesquard.app.issue.dto.response.IssueReadResponse;
import codesquard.app.issue.dto.response.IssueUserResponse;
import codesquard.app.issue.dto.response.IssuesResponse;
import codesquard.app.issue.mapper.IssueMapper;
import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class IssueQueryService {

	private final IssueRepository issueRepository;
	private final IssueMapper issueMapper;

	public IssueReadResponse get(Long issueId) {
		validateExistIssue(issueId);
		IssueReadResponse issueReadResponse = issueRepository.findBy(issueId);
		List<IssueUserResponse> assignees = IssueUserResponse.from(issueRepository.findAssigneesBy(issueId));
		List<IssueLabelResponse> labels = IssueLabelResponse.from(issueRepository.findLabelsBy(issueId));
		IssueMilestoneCountResponse issueMilestoneCountResponse = countIssueForMilestone(
			issueReadResponse.getMilestone());
		List<IssueCommentsResponse> issueCommentsResponse = issueRepository.findCommentsBy(issueId);
		return issueReadResponse.from(assignees, labels, issueMilestoneCountResponse, issueCommentsResponse);
	}

	private IssueMilestoneCountResponse countIssueForMilestone(IssueMilestoneResponse milestone) {
		if (milestone != null) {
			return issueRepository.countIssueBy(milestone.getId());
		}
		return null;
	}

	public void validateExistIssue(Long issueId) {
		if (!issueRepository.isExist(issueId)) {
			throw new NoSuchIssueException();
		}
	}

	public IssueReadResponse findById(Long issueId) {
		return issueRepository.findBy(issueId);
	}

	public List<IssueUserResponse> findAssigneesById(Long issueId) {
		return IssueUserResponse.from(issueRepository.findAssigneesBy(issueId));
	}

	public List<IssueLabelResponse> findLabelsById(Long issueId) {
		return IssueLabelResponse.from(issueRepository.findLabelsBy(issueId));
	}

	public List<IssuesResponse> findFilterIssues(IssueFilterRequest request) {
		return issueMapper.getIssues(request);
	}

}
