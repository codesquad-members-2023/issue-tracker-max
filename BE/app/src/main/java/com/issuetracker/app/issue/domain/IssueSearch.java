package com.issuetracker.app.issue.domain;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssueSearch {

	private Boolean isOpen;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;
	private Long authorId;
	private Long commentAuthorId;

	@Builder
	private IssueSearch(Boolean isOpen, List<Long> assigneeIds, List<Long> labelIds, Long milestoneId, Long authorId,
		Long commentAuthorId) {
		this.isOpen = isOpen;
		this.assigneeIds = assigneeIds;
		this.labelIds = labelIds;
		this.milestoneId = milestoneId;
		this.authorId = authorId;
		this.commentAuthorId = commentAuthorId;
	}
}
