package codesquard.app.issue.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquard.app.comment.repository.CommentRepository;
import codesquard.app.issue.dto.request.IssueModifyAssigneesRequest;
import codesquard.app.issue.dto.request.IssueModifyContentRequest;
import codesquard.app.issue.dto.request.IssueModifyLabelsRequest;
import codesquard.app.issue.dto.request.IssueModifyMilestoneRequest;
import codesquard.app.issue.dto.request.IssueModifyStatusesRequest;
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

	public void modifyTitle(IssueModifyTitleRequest issueModifyTitleRequest, Long issueId, Long userId) {
		issueQueryService.validateIssueAuthor(issueId, userId);
		issueRepository.modifyTitle(issueModifyTitleRequest.getTitle(), issueId, LocalDateTime.now());
	}

	public void modifyContent(IssueModifyContentRequest issueModifyTitleRequest, Long issueId, Long userId) {
		issueQueryService.validateIssueAuthor(issueId, userId);
		issueRepository.modifyContent(issueModifyTitleRequest.getContent(), issueId, LocalDateTime.now());
	}

	public void modifyAssignees(IssueModifyAssigneesRequest issueModifyAssigneesRequest, Long issueId, Long userId) {
		issueQueryService.validateIssueAuthor(issueId, userId);
		issueRepository.deleteIssueAssigneesBy(issueId);
		issueRepository.saveIssueAssignee(issueId, issueModifyAssigneesRequest.getAssignees());
	}

	public void modifyMilestone(IssueModifyMilestoneRequest issueModifyMilestoneRequest, Long issueId, Long userId) {
		issueQueryService.validateIssueAuthor(issueId, userId);
		issueRepository.modifyMilestone(issueModifyMilestoneRequest.getMilestone(), issueId);
	}

	public void modifyLabels(IssueModifyLabelsRequest issueModifyLabelsRequest, Long issueId, Long userId) {
		issueQueryService.validateIssueAuthor(issueId, userId);
		issueRepository.deleteIssueLabelsBy(issueId);
		issueRepository.saveIssueLabel(issueId, issueModifyLabelsRequest.getLabels());
	}

	public void delete(Long issueId, Long userId) {
		issueQueryService.validateIssueAuthor(issueId, userId);
		issueRepository.deleteBy(issueId);
		commentRepository.deleteByIssueId(issueId);
	}

	public void modifyStatuses(IssueModifyStatusesRequest issueModifyStatusesRequest, Long userId) {
		IssueStatus issueStatus = IssueStatus.validateIssueStatus(issueModifyStatusesRequest.getStatus());
		issueQueryService.validateIssuesAuthor(issueModifyStatusesRequest.getIssues(), userId);
		issueRepository.modifyStatuses(issueStatus.name(), issueModifyStatusesRequest.getIssues(), LocalDateTime.now());
	}
}
