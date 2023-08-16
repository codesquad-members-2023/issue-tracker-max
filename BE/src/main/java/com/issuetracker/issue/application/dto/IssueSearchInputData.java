package com.issuetracker.issue.application.dto;

import java.util.List;

import com.issuetracker.issue.domain.IssueSearch;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueSearchInputData {

	private Boolean isOpen;
	private List<String> assigneeNames;
	private List<String> labelTitles;
	private String milestoneTitle;
	private String authorName;
	private Long commentAuthorId;
	private List<String> no;

	public IssueSearch toIssueSearch() {
		return IssueSearch.builder()
			.isOpen(isOpen)
			.assigneeNames(assigneeNames)
			.labelTitles(labelTitles)
			.milestoneTitle(milestoneTitle)
			.authorName(authorName)
			.commentAuthorId(commentAuthorId)
			.no(no)
			.build();
	}
}
