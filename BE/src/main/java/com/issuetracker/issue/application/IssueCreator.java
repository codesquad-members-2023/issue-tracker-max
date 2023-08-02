package com.issuetracker.issue.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.issuetracker.issue.application.dto.IssueCreateInputData;
import com.issuetracker.issue.domain.Assignee;
import com.issuetracker.issue.domain.IssueLabelMapping;
import com.issuetracker.issue.infrastrucure.AssigneeRepository;
import com.issuetracker.issue.infrastrucure.IssueLabelMappingRepository;
import com.issuetracker.issue.infrastrucure.IssueRepository;
import com.issuetracker.label.application.LabelValidator;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.application.MemberValidator;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.application.MilestoneValidator;
import com.issuetracker.milestone.domain.Milestone;

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

		Long savedId = issueRepository.save(issueCreateData.toIssue(issueVerifiedCreator.author, issueVerifiedCreator.labels, issueVerifiedCreator.milestone, LocalDateTime.now()));
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

	static class IssueVerifiedCreator {
		private final Member author;
		private final List<Member> assignees;
		private final List<Label> labels;
		private final Milestone milestone;

		public IssueVerifiedCreator(Long authorId, List<Long> assigneeIds, List<Long> labelIds, Long milestoneId) {
			this.author = Member.createInstanceById(authorId);
			this.assignees = assigneeIds.stream()
				.map(Member::createInstanceById)
				.collect(Collectors.toUnmodifiableList());
			this.labels = labelIds.stream()
				.map(Label::createInstanceById)
				.collect(Collectors.toUnmodifiableList());
			this.milestone = Milestone.createInstanceById(milestoneId);
		}

		public List<Assignee> getAssignees(Long issueId) {
			return assignees.stream()
				.map(m -> Assignee.builder()
					.issueId(issueId)
					.memberId(m.getId())
					.build())
				.collect(Collectors.toUnmodifiableList());
		}

		public List<IssueLabelMapping> getIssueLabelMappings(Long issueId) {
			return labels.stream().map(label -> IssueLabelMapping.builder()
					.issueId(issueId)
					.labelId(label.getId())
					.build())
				.collect(Collectors.toUnmodifiableList());
		}
	}
}
