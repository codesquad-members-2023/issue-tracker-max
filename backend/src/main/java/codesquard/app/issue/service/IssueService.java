package codesquard.app.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.errors.errorcode.IssueErrorCode;
import codesquard.app.errors.exception.IllegalIssueStatusException;
import codesquard.app.errors.exception.NoSuchIssueException;
import codesquard.app.issue.dto.request.IssueModifyAssigneesRequest;
import codesquard.app.issue.dto.request.IssueModifyContentRequest;
import codesquard.app.issue.dto.request.IssueModifyLabelsRequest;
import codesquard.app.issue.dto.request.IssueModifyMilestoneRequest;
import codesquard.app.issue.dto.request.IssueModifyStatusRequest;
import codesquard.app.issue.dto.request.IssueModifyTitleRequest;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.entity.Issue;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.issue.repository.IssueRepository;
import codesquard.app.label.entity.Label;
import codesquard.app.user.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueRepository issueRepository;
	private final CommentRepository commentRepository;

	@Transactional
	public Long save(IssueSaveRequest issueSaveRequest, Long userId) {
		Long id = issueRepository.save(issueSaveRequest.toEntity(userId));
		registerIssueLabel(id, issueSaveRequest.getLabels());
		registerIssueAssignee(id, issueSaveRequest.getAssignees());
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

	@Transactional
	public void modifyStatus(IssueModifyStatusRequest issueModifyStatusRequest, Long issueId) {
		existIssue(issueId);
		IssueStatus issueStatus;
		try {
			issueStatus = IssueStatus.valueOf(issueModifyStatusRequest.getStatus());
		} catch (IllegalArgumentException e) {
			throw new IllegalIssueStatusException(IssueErrorCode.INVALID_ISSUE_STATUS);
		}
		issueRepository.modifyStatus(issueStatus.name(), issueId);
	}

	@Transactional
	public void modifyTitle(IssueModifyTitleRequest issueModifyTitleRequest, Long issueId) {
		existIssue(issueId);
		issueRepository.modifyTitle(issueModifyTitleRequest.getTitle(), issueId);
	}

	@Transactional
	public void modifyContent(IssueModifyContentRequest issueModifyTitleRequest, Long issueId) {
		existIssue(issueId);
		issueRepository.modifyContent(issueModifyTitleRequest.getContent(), issueId);
	}

	@Transactional
	public void modifyAssignees(IssueModifyAssigneesRequest issueModifyAssigneesRequest, Long issueId) {
		existIssue(issueId);
		issueRepository.deleteIssueAssigneesById(issueId);
		registerIssueAssignee(issueId, issueModifyAssigneesRequest.getAssignees());
	}

	@Transactional
	public void modifyMilestone(IssueModifyMilestoneRequest issueModifyMilestoneRequest, Long issueId) {
		existIssue(issueId);
		issueRepository.modifyMilestone(issueModifyMilestoneRequest.getMilestone(), issueId);
	}

	@Transactional
	public void modifyLabels(IssueModifyLabelsRequest issueModifyLabelsRequest, Long issueId) {
		existIssue(issueId);
		issueRepository.deleteIssueLabelsById(issueId);
		registerIssueLabel(issueId, issueModifyLabelsRequest.getLabels());
	}

	@Transactional(readOnly = true)
	public Issue findById(Long issueId) {
		return issueRepository.findById(issueId);
	}

	@Transactional(readOnly = true)
	public List<User> findAssigneesById(Long issueId) {
		return issueRepository.findAssigneesById(issueId);
	}

	@Transactional(readOnly = true)
	public List<Label> findLabelsById(Long issueId) {
		return issueRepository.findLabelsById(issueId);
	}

	@Transactional
	private void existIssue(Long issueId) {
		if (!issueRepository.exist(issueId)) {
			throw new NoSuchIssueException(IssueErrorCode.NOT_FOUND_ISSUE);
		}
	}

	@Transactional
	public void delete(Long issueId) {
		existIssue(issueId);
		issueRepository.deleteById(issueId);
		commentRepository.deleteByIssueId(issueId);
	}
}
