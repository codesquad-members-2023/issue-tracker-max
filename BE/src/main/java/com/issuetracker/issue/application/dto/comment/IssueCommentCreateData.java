package com.issuetracker.issue.application.dto.comment;

import java.time.LocalDateTime;

import com.issuetracker.issue.domain.comment.IssueComment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCommentCreateData {

	private Long issueId;
	private String content;
	private Long authorId;

	public IssueComment toIssueComment(LocalDateTime now) {
		return IssueComment.builder()
			.issueId(issueId)
			.content(content)
			.createAt(now)
			.authorId(authorId)
			.build();
	}
}
