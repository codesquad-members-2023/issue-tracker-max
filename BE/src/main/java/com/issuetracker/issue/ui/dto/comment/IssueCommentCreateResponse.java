package com.issuetracker.issue.ui.dto.comment;

import com.issuetracker.issue.application.dto.comment.IssueCommentCreateInformation;

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
