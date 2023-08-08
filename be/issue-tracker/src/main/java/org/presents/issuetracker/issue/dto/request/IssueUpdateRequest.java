package org.presents.issuetracker.issue.dto.request;

import lombok.Getter;

@Getter
public class IssueUpdateRequest {
	private Long id;
	private String title;
	private String contents;
}
