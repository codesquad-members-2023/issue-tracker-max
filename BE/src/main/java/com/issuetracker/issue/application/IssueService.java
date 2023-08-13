package com.issuetracker.issue.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCreateData;
import com.issuetracker.issue.application.dto.assignee.AssigneeCandidatesInformation;
import com.issuetracker.issue.application.dto.assignee.AssigneeCreateData;
import com.issuetracker.issue.application.dto.assignee.AssigneeCreateInformation;
import com.issuetracker.issue.application.dto.comment.IssueCommentCreateData;
import com.issuetracker.issue.application.dto.comment.IssueCommentCreateInformation;
import com.issuetracker.issue.application.dto.comment.IssueCommentUpdateData;
import com.issuetracker.issue.application.dto.IssueCreateInformation;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.IssueDetailInformation;
import com.issuetracker.issue.application.dto.IssueSearchInformation;
import com.issuetracker.issue.application.dto.IssueSearchInputData;
import com.issuetracker.issue.application.dto.IssueUpdateData;
import com.issuetracker.issue.application.dto.IssuesCountInformation;
import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCandidatesInformation;
import com.issuetracker.issue.domain.assignedlabel.AssignedLabelRepository;
import com.issuetracker.issue.domain.assignee.AssigneeRepository;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.comment.IssueCommentRepository;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.issue.domain.IssueMapper;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.member.application.dto.MemberInformation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {

	private final IssueValidator issueValidator;
	private final IssueMapper issueMapper;
	private final IssueRepository issueRepository;
	private final IssueCommentRepository issueCommentRepository;
	private final AssigneeRepository assigneeRepository;
	private final AssignedLabelRepository assignedLabelRepository;

	@Transactional
	public IssueCreateInformation create(IssueCreateInputData issueCreateData) {
		issueValidator.verifyCreate(issueCreateData);

		Issue issue = issueCreateData.toIssue(LocalDateTime.now());
		Long savedId = issueRepository.save(issue);
		assigneeRepository.saveAll(issueCreateData.toAssignees(savedId));
		assignedLabelRepository.saveAll(issueCreateData.toAssignedLabel(savedId));
		return IssueCreateInformation.from(savedId);
	}

	public IssuesCountInformation findIssuesCount() {
		return IssuesCountInformation.from(issueRepository.findAllCount());
	}

	public List<IssueSearchInformation> search(IssueSearchInputData issueSearchInputData) {
		return IssueSearchInformation.from(issueMapper.search(issueSearchInputData.toIssueSearch()));
	}

	public List<MemberInformation> searchAssignee() {
		return MemberInformation.from(assigneeRepository.findAll());
	}

	public List<LabelInformation> searchAssignedLabel() {
		return LabelInformation.from(assignedLabelRepository.findAll());
	}

	public IssueDetailInformation findById(Long id) {
		IssueDetailRead issueDetailRead = issueMapper.findById(id);
		issueValidator.verifyIssueDetail(issueDetailRead);
		return IssueDetailInformation.from(issueDetailRead);
	}

	@Transactional
	public void updateIssueOpen(IssueUpdateData issueUpdateData) {
		int updatedCount = issueRepository.updateOpen(issueUpdateData.getId(), issueUpdateData.getIsOpen());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void updateIssueTitle(IssueUpdateData issueUpdateData) {
		issueValidator.verifyNonNull(issueUpdateData.getTitle());
		int updatedCount = issueRepository.updateTitle(issueUpdateData.getId(), issueUpdateData.getTitle());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void updateIssueContent(IssueUpdateData issueUpdateData) {
		issueValidator.verifyNonNull(issueUpdateData.getContent());
		int updatedCount = issueRepository.updateContent(issueUpdateData.getId(), issueUpdateData.getContent());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void deleteIssue(long id) {
		int deletedCount = issueRepository.delete(id);
		issueValidator.verifyUpdatedOrDeletedCount(deletedCount);
	}

	@Transactional
	public IssueCommentCreateInformation createIssueComment(IssueCommentCreateData issueCommentCreateData) {
		issueValidator.verifyCreateIssueComment(issueCommentCreateData.getIssueId(),
			issueCommentCreateData.getAuthorId());
		Long savedId = issueCommentRepository.save(issueCommentCreateData.toIssueComment(LocalDateTime.now()));
		return IssueCommentCreateInformation.from(savedId);
	}

	@Transactional
	public void updateIssueCommentContent(IssueCommentUpdateData issueCommentUpdateData) {
		issueValidator.verifyNonNull(issueCommentUpdateData.getContent());
		int updatedCount = issueCommentRepository.updateContent(issueCommentUpdateData.getIssueCommentId(),
			issueCommentUpdateData.getContent());
		issueValidator.verifyCommentUpdatedOrDeletedCount(updatedCount);
	}

	@Transactional
	public void updateIssueMilestone(IssueUpdateData issueUpdateData) {
		issueValidator.verifyUpdateMilestone(issueUpdateData.getMilestoneId());
		int updatedCount = issueRepository.updateMilestone(issueUpdateData.getId(), issueUpdateData.getMilestoneId());
		issueValidator.verifyUpdatedOrDeletedCount(updatedCount);
	}

	public AssigneeCandidatesInformation searchAssigneeCandidates(long issueId) {

		return AssigneeCandidatesInformation
			.from(
				assigneeRepository.findAllAssignedToIssue(issueId),
				assigneeRepository.findAllUnassignedToIssue(issueId)
			);
	}

	public AssignedLabelCandidatesInformation searchLabelCandidates(long issueId) {

		return AssignedLabelCandidatesInformation
			.from(
				assignedLabelRepository.findAllAssignedToIssue(issueId),
				assignedLabelRepository.findAllUnassignedToIssue(issueId)
			);
	}

	@Transactional
	public AssigneeCreateInformation createAssignee(AssigneeCreateData assigneeCreateData) {
		issueValidator.verifyCreateAssignee(assigneeCreateData);
		return AssigneeCreateInformation.from(assigneeRepository.save(assigneeCreateData.toAssignee()));
	}

	@Transactional
	public int deleteAssignee(Long assigneeId) {
		return assigneeRepository.delete(assigneeId);
	}

	@Transactional
	public AssigneeCreateInformation createAssignedLabel(AssignedLabelCreateData assignedLabelCreateData) {
		issueValidator.verifyCreateAssignedLabel(assignedLabelCreateData);
		return AssigneeCreateInformation.from(assignedLabelRepository.save(assignedLabelCreateData.toAssignedLabel()));
	}

	@Transactional
	public int deleteAssignedLabel(Long assignedLabelId) {
		return assignedLabelRepository.delete(assignedLabelId);
	}
}
