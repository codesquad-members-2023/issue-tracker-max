package com.issuetracker.issue.application.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCommentUpdateData {

	private Long issueId;
	private Long issueCommentId;
	private String content;
}
