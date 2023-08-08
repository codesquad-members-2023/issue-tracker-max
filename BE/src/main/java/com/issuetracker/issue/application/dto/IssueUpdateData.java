package com.issuetracker.issue.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class IssueUpdateData {

	private long id;
	private Boolean isOpen;
	private String title;
	private String content;

	public static IssueUpdateData createIssueUpdateDataOpen(long id, Boolean isOpen) {
		return new IssueUpdateData(id, isOpen, null, null);
	}

	public static IssueUpdateData createIssueUpdateDataTitle(long id, String title) {
		return new IssueUpdateData(id, null, title, null);
	}

	public static IssueUpdateData createIssueUpdateDataContent(long id, String content) {
		return new IssueUpdateData(id, null, null, content);
	}
}
