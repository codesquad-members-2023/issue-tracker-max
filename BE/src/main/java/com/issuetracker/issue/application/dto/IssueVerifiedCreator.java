package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.Assignee;
import com.issuetracker.issue.domain.IssueLabelMapping;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.domain.Milestone;

import lombok.Getter;

@Getter
public class IssueVerifiedCreator {
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
