package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.IssueAssigneeInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueAssigneesResponse {

	private List<IssueAssigneeResponse> assignees;

	public static IssueAssigneesResponse from(List<IssueAssigneeInformation> issueAssigneeInformation) {
		return new IssueAssigneesResponse(IssueAssigneeResponse.from(issueAssigneeInformation));
	}
}
