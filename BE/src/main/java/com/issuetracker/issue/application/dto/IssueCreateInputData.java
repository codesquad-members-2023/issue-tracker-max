package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.AssignedLabel;
import com.issuetracker.issue.domain.Assignee;
import com.issuetracker.issue.domain.Issue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCreateInputData {

	private String title;
	private String content;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
	private Long authorId;

	public Issue toIssue(LocalDateTime now) {
		return Issue.builder()
			.title(title)
			.content(content)
			.isOpen(true)
			.createAt(now)
			.authorId(authorId)
			.milestoneId(milestoneId)
			.build();
	}

	public List<Assignee> toAssignees(Long issueId) {
		return assigneeIds.stream()
			.map(memberId -> Assignee.builder()
				.issueId(issueId)
				.memberId(memberId)
				.build())
			.collect(Collectors.toUnmodifiableList());
	}

	public List<AssignedLabel> toAssignedLabel(Long issueId) {
		return labelIds.stream()
			.map(labelId -> AssignedLabel.builder()
				.issueId(issueId)
				.labelId(labelId)
				.build())
			.collect(Collectors.toUnmodifiableList());
	}
}
