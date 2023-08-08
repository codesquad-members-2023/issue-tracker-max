package com.issuetracker.issue.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCommentUpdateData {

	private Long issueId;
	private Long issueCommentId;
	private String content;
}
