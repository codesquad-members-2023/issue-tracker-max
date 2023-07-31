package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.Assignee;
import com.issuetracker.issue.domain.Issue;
import com.issuetracker.issue.domain.IssueLabelMapping;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.domain.Milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCreateData {

	private String title;
	private String content;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;

	public Issue toIssue() {
		return Issue.builder()
			.title(title)
			.content(content)
			.isOpen(true)
			.createAt(LocalDateTime.now())
			.author(Member.createInstanceById(1L))
			.labels(assigneeIds.stream()
				.map(Label::createInstanceById)
				.collect(Collectors.toUnmodifiableList()))
			.milestone(Milestone.createInstanceById(milestoneId))
			.build();
	}

	public List<Assignee> toAssignee(Long issueId) {
		return assigneeIds.stream()
			.map(assignee -> Assignee.builder()
				.issueId(issueId)
				.memberId(assignee)
				.build())
			.collect(Collectors.toUnmodifiableList());
	}

	public List<IssueLabelMapping> toIssueLabelMappings(Long issueId) {
		return labelIds.stream()
			.map(label -> IssueLabelMapping.builder()
				.issueId(issueId)
				.labelId(label)
				.build())
			.collect(Collectors.toUnmodifiableList());
	}
}
