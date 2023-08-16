package com.issuetracker.issue.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IssueSearch {

	private Boolean isOpen;
	private List<String> assigneeNames;
	private List<String> labelTitles;
	private String milestoneTitle;
	private String authorName;
	private Long commentAuthorId;
	private List<String> no;

	@Builder
	public IssueSearch(Boolean isOpen, List<String> assigneeNames, List<String> labelTitles, String milestoneTitle,
		String authorName, Long commentAuthorId, List<String> no) {
		this.isOpen = isOpen;
		this.assigneeNames = assigneeNames;
		this.labelTitles = labelTitles;
		this.milestoneTitle = milestoneTitle;
		this.authorName = authorName;
		this.commentAuthorId = commentAuthorId;
		this.no = no;
	}
}
