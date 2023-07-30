package com.issuetracker.app.issue.ui.dto;

import java.util.List;

import com.issuetracker.app.issue.application.dto.IssueCreateData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class IssueCreateRequest {

	private String title;
	private String content;
	private List<Long> assigneeIds;
	private List<Long> labelIds;
	private Long milestoneId;

	public IssueCreateData toIssueCreateData() {
		return new IssueCreateData(
			title,
			content,
			assigneeIds,
			labelIds,
			milestoneId
		);
	}
}
