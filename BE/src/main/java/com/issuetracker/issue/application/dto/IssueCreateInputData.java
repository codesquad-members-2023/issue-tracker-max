package com.issuetracker.issue.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.Issue;
import com.issuetracker.label.domain.Label;
import com.issuetracker.member.domain.Member;
import com.issuetracker.milestone.domain.Milestone;

import lombok.Getter;

@Getter
public class IssueCreateInputData {

	private String title;
	private String content;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
	private Long author;

	public IssueCreateInputData(String title, String content, List<Long> assigneeIds, List<Long> labelIds,
		Long milestoneId, Long author) {
		this.title = title;
		this.content = content;
		this.assigneeIds = getNonNullLabels(assigneeIds);
		this.labelIds = getNonNullLabels(labelIds);
		this.milestoneId = milestoneId;
		this.author = author;
	}

	public Issue toIssue(Member author, List<Label> labels, Milestone milestone, LocalDateTime now) {
		return Issue.builder()
			.title(title)
			.content(content)
			.isOpen(true)
			.createAt(now)
			.author(author)
			.labels(labels)
			.milestone(milestone)
			.build();
	}

	private List<Long> getNonNullLabels(List<Long> ids) {
		return ids.stream()
			.filter(Objects::nonNull)
			.collect(Collectors.toUnmodifiableList());
	}
}
