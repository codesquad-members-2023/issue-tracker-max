package codesquard.app.issue.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.issue.dto.request.IssueModifyAssigneesRequest;
import codesquard.app.issue.dto.request.IssueModifyContentRequest;
import codesquard.app.issue.dto.request.IssueModifyLabelsRequest;
import codesquard.app.issue.dto.request.IssueModifyMilestoneRequest;
import codesquard.app.issue.dto.request.IssueModifyStatusRequest;
import codesquard.app.issue.dto.request.IssueModifyTitleRequest;
import codesquard.app.issue.dto.request.IssueSaveRequest;
import codesquard.app.issue.entity.IssueStatus;
import codesquard.app.issue.repository.IssueRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class IssueService {

	private final IssueQueryService issueQueryService;
	private final IssueRepository issueRepository;
	private final CommentRepository commentRepository;

	public Long save(IssueSaveRequest issueSaveRequest, Long userId) {
		Long id = issueRepository.save(issueSaveRequest.toEntity(userId));
		issueRepository.saveIssueLabel(id, issueSaveRequest.getLabels());
		issueRepository.saveIssueAssignee(id, issueSaveRequest.getAssignees());
		return id;
	}

	public void modifyStatus(IssueModifyStatusRequest issueModifyStatusRequest, Long issueId) {
		IssueStatus issueStatus = IssueStatus.validateIssueStatus(issueModifyStatusRequest.getStatus());
		issueQueryService.validateExistIssue(issueId);
		issueRepository.modifyStatus(issueStatus.name(), issueId);
	}

	public void modifyTitle(IssueModifyTitleRequest issueModifyTitleRequest, Long issueId) {
		issueQueryService.validateExistIssue(issueId);
		issueRepository.modifyTitle(issueModifyTitleRequest.getTitle(), issueId);
	}

	public void modifyContent(IssueModifyContentRequest issueModifyTitleRequest, Long issueId) {
		issueQueryService.validateExistIssue(issueId);
		issueRepository.modifyContent(issueModifyTitleRequest.getContent(), issueId);
	}

	public void modifyAssignees(IssueModifyAssigneesRequest issueModifyAssigneesRequest, Long issueId) {
		issueQueryService.validateExistIssue(issueId);
		issueRepository.deleteIssueAssigneesBy(issueId);
		issueRepository.saveIssueAssignee(issueId, issueModifyAssigneesRequest.getAssignees());
	}

	public void modifyMilestone(IssueModifyMilestoneRequest issueModifyMilestoneRequest, Long issueId) {
		issueQueryService.validateExistIssue(issueId);
		issueRepository.modifyMilestone(issueModifyMilestoneRequest.getMilestone(), issueId);
	}

	public void modifyLabels(IssueModifyLabelsRequest issueModifyLabelsRequest, Long issueId) {
		issueQueryService.validateExistIssue(issueId);
		issueRepository.deleteIssueLabelsBy(issueId);
		issueRepository.saveIssueLabel(issueId, issueModifyLabelsRequest.getLabels());
	}

	public void delete(Long issueId) {
		issueQueryService.validateExistIssue(issueId);
		issueRepository.deleteBy(issueId);
		commentRepository.deleteByIssueId(issueId);
	}
}
