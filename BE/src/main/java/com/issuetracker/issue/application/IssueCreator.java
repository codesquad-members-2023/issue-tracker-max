package com.issuetracker.issue.application;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.application.dto.IssueVerifiedCreator;
import com.issuetracker.issue.domain.AssigneeRepository;
import com.issuetracker.issue.domain.IssueLabelMappingRepository;
import com.issuetracker.issue.domain.IssueRepository;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.milestone.application.MilestoneValidator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IssueCreator {

	private final MilestoneValidator milestoneValidator;
	private final MemberValidator memberValidator;
	private final LabelValidator labelValidator;

	private final IssueLabelMappingRepository issueLabelMappingRepository;
	private final AssigneeRepository assigneeRepository;
	private final IssueRepository issueRepository;

	public Long create(IssueCreateInputData issueCreateData) {
		IssueVerifiedCreator issueVerifiedCreator = verify(issueCreateData);

		Long savedId = issueRepository.save(issueCreateData.toIssue(issueVerifiedCreator.getAuthor(), issueVerifiedCreator.getLabels(), issueVerifiedCreator.getMilestone(), LocalDateTime.now()));
		assigneeRepository.saveAll(issueVerifiedCreator.getAssignees(savedId));
		issueLabelMappingRepository.saveAll(issueVerifiedCreator.getIssueLabelMappings(savedId));
		return savedId;
	}

	private IssueVerifiedCreator verify(IssueCreateInputData issueCreateData) {
		milestoneValidator.verifyMilestone(issueCreateData.getMilestoneId());
		memberValidator.verifyMember(issueCreateData.getAuthor());
		memberValidator.verifyMembers(issueCreateData.getAssigneeIds());
		labelValidator.verifyLabels(issueCreateData.getLabelIds());

		return new IssueVerifiedCreator(
			1L,
			issueCreateData.getAssigneeIds(),
			issueCreateData.getLabelIds(),
			issueCreateData.getMilestoneId()
		);
	}
}
