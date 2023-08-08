package com.issuetracker.issue.ui.dto;

import com.issuetracker.issue.application.dto.IssueUpdateData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueUpdateRequest {

	private Boolean isOpen;
	private String title;
	private String content;

	public IssueUpdateData toIssueUpdateDataOpen(long id) {
		return IssueUpdateData.createIssueUpdateDataOpen(id, isOpen);
	}

	public IssueUpdateData toIssueUpdateDataTitle(long id) {
		return IssueUpdateData.createIssueUpdateDataTitle(id, title);
	}

	public IssueUpdateData toIssueUpdateDataContent(long id) {
		return IssueUpdateData.createIssueUpdateDataContent(id, content);
	}
}
