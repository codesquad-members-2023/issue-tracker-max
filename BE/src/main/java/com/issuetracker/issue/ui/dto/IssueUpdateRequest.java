package com.issuetracker.issue.ui.dto;

import org.hibernate.validator.constraints.Length;

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

	@Length(min = 1, max = 100, message = "이슈 제목은 1이상 100자만 가능합니다.")
	private String title;

	@Length(max = 3000, message = "이슈 내용은 최대 3000자 입니다.")
	private String content;

	private Long milestoneId;

	public IssueUpdateData toIssueUpdateDataOpen(long id) {
		return IssueUpdateData.createIssueUpdateDataOpen(id, isOpen);
	}

	public IssueUpdateData toIssueUpdateDataTitle(long id) {
		return IssueUpdateData.createIssueUpdateDataTitle(id, title);
	}

	public IssueUpdateData toIssueUpdateDataContent(long id) {
		return IssueUpdateData.createIssueUpdateDataContent(id, content);
	}

	public IssueUpdateData toIssueUpdateDataMilestone(long id) {
		return IssueUpdateData.createIssueUpdateDataMilestone(id, milestoneId);
	}
}
