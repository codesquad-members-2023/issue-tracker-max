package com.issuetrackermax.domain.issue.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueWithComment {
	private Long id;
	private Long issueId;
	private Long commentId;

	@Builder
	public IssueWithComment(Long id, Long issueId, Long commentId) {
		this.id = id;
		this.issueId = issueId;
		this.commentId = commentId;
	}
}
