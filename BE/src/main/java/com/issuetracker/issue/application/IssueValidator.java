package com.issuetracker.issue.application;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.issuetracker.config.exception.CustomHttpException;
import com.issuetracker.config.exception.ErrorType;
import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.domain.IssueDetailRead;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.milestone.application.MilestoneValidator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IssueValidator {

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
}
