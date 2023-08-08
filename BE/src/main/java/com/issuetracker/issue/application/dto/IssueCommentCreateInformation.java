package com.issuetracker.issue.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueCommentCreateInformation {

	private Long id;

	public static IssueCommentCreateInformation from(Long savedId) {
		return new IssueCommentCreateInformation(savedId);
	}
}
