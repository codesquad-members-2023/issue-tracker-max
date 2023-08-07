package com.issuetracker.issue.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueComment {

	private Long id;
	private String content;
	private LocalDateTime createAt;
	private Long issueId;
	private Long authorId;

	@Builder
	private IssueComment(Long id, String content, LocalDateTime createAt, Long issueId, Long authorId) {
		this.id = id;
		this.content = content;
		this.createAt = createAt;
		this.issueId = issueId;
		this.authorId = authorId;
	}
}
