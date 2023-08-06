package com.issuetracker.issue.application.dto;

import java.util.List;

import com.issuetracker.issue.domain.IssueSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueSearchInputData {

	private Boolean isOpen;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
	private Long authorId;
	private Long commentAuthorId;

	public IssueSearch toIssueSearch() {
		return IssueSearch.builder()
			.isOpen(isOpen)
			.assigneeIds(assigneeIds)
			.labelIds(labelIds)
			.milestoneId(milestoneId)
			.authorId(authorId)
			.commentAuthorId(commentAuthorId)
			.build();
	}
}
