package com.issuetracker.issue.application;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCreateData;
import com.issuetracker.issue.application.dto.assignee.AssigneeCreateData;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.issue.domain.assignedlabel.AssignedLabelRepository;
import com.issuetracker.issue.domain.assignee.AssigneeRepository;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.application.MilestoneValidator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IssueValidator {

	private final IssueRepository issueRepository;
	private final AssigneeRepository assigneeRepository;
	private final AssignedLabelRepository assignedLabelRepository;
	private final MilestoneValidator milestoneValidator;
	private final MemberValidator memberValidator;
	private final LabelValidator labelValidator;

	public void verifyCreate(IssueCreateInputData issueCreateInputData) {
		milestoneValidator.verifyMilestone(issueCreateInputData.getMilestoneId());
		memberValidator.verifyMember(issueCreateInputData.getAuthorId());
		memberValidator.verifyMembers(issueCreateInputData.getAssigneeIds());
		labelValidator.verifyLabels(issueCreateInputData.getLabelIds());
	}

	public void verifyIssueDetail(IssueDetailRead issueDetailRead) {
		if (Objects.isNull(issueDetailRead)) {
			throw new CustomHttpException(ErrorType.ISSUE_NOT_FOUND);
		}
	}

	public void verifyUpdatedOrDeletedCount(int count) {
		if (count != 1) {
			throw new CustomHttpException(ErrorType.ISSUE_NOT_FOUND);
		}
	}

	public void verifyCommentUpdatedOrDeletedCount(int count) {
		if (count != 1) {
			throw new CustomHttpException(ErrorType.ISSUE_COMMENT_NOT_FOUND);
		}
	}

	public void verifyNonNull(Object object) {
		if (Objects.isNull(object)) {
			throw new CustomHttpException(ErrorType.ISSUE_UPDATE_NULL);
		}
	}

	public void verifyCreateIssueComment(Long id, Long authorId) {
		if (Objects.nonNull(id) && !issueRepository.existById(id)) {
			throw new CustomHttpException(ErrorType.ISSUE_NOT_FOUND);
		}

		memberValidator.verifyMember(authorId);
	}

	public void verifyCreateAssignee(AssigneeCreateData assigneeCreateData) {
		verifyIssue(assigneeCreateData.getIssueId());
		memberValidator.verifyMember(assigneeCreateData.getMemberId());

		List<Member> assignees = assigneeRepository.findAllAssignedToIssue(assigneeCreateData.getIssueId());

		if (assignees.stream().anyMatch(m -> m.equalsId(assigneeCreateData.getMemberId()))) {
			throw new CustomHttpException(ErrorType.ASSIGNEE_DUPLICATION);
		}
	}

	public void verifyIssue(Long id) {
		if(Objects.nonNull(id) && !issueRepository.existById(id)) {
			throw new CustomHttpException(ErrorType.ISSUE_NOT_FOUND);
		}
	}

	public void verifyCreateAssignedLabel(AssignedLabelCreateData assignedLabelCreateData) {
		verifyIssue(assignedLabelCreateData.getIssueId());
		labelValidator.verifyLabel(assignedLabelCreateData.getLabelId());

		List<Label> assignedLabels = assignedLabelRepository.findAllAssignedToIssue(assignedLabelCreateData.getIssueId());

		if (assignedLabels.stream().anyMatch(l -> l.equalsId(assignedLabelCreateData.getLabelId()))) {
			throw new CustomHttpException(ErrorType.ASSIGNED_LABEL_DUPLICATION);
		}
	}

	public void verifyUpdateMilestone(Long milestoneId) {
		milestoneValidator.verifyMilestone(milestoneId);
	}
}
