package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.IssueSearchInputData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueSearchRequest {

	private Boolean isOpen;
	private List<String> assigneeNames;
	private List<String> labelTitles;
	private String milestoneTitle;
	private String authorName;
	private Boolean isCommentedByMe;
	private List<String> no;

	public IssueSearchInputData toIssueSearchData(Long loginMemberId) { // 여기서 로그인한 유저 아이디 받기
		return new IssueSearchInputData(
			isOpen,
			assigneeNames,
			labelTitles,
			milestoneTitle,
			authorName,
			getCommentAuthorId(loginMemberId),
			no
		);
	}

	private Long getCommentAuthorId(Long loginMemberId) {
		if (isCommentedByMe == null || isCommentedByMe == Boolean.FALSE) {
			return null;
		}

		return loginMemberId;
	}
}
