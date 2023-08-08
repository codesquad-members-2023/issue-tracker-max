package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.AssigneesForIssueUpdateInformation;
import com.issuetracker.member.ui.dto.MemberResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneesForIssueUpdateResponse {

	private List<MemberResponse> assignees;
	private List<MemberResponse> members;

	public static AssigneesForIssueUpdateResponse from(
		AssigneesForIssueUpdateInformation assigneesForIssueUpdateInformation) {

		return new AssigneesForIssueUpdateResponse(
			MemberResponse.from(assigneesForIssueUpdateInformation.getAssignees()),
			MemberResponse.from(assigneesForIssueUpdateInformation.getMembers())
		);
	}
}
