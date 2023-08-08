package com.issuetracker.issue.ui.dto;

import com.issuetracker.issue.application.dto.IssueCommentCreateInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueCommentCreateResponse {

	private Long id;

	public static IssueCommentCreateResponse from(IssueCommentCreateInformation issueCommentCreateInformation) {
		return new IssueCommentCreateResponse(
			issueCommentCreateInformation.getId()
		);
	}
}
