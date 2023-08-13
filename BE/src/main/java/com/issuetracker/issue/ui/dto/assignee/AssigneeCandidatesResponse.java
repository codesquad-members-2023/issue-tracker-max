package com.issuetracker.issue.ui.dto.assignee;

import java.util.List;

import com.issuetracker.issue.application.dto.assignee.AssigneeCandidatesInformation;
import com.issuetracker.member.ui.dto.MemberResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneeCandidatesResponse {

	private List<MemberResponse> assignees;
	private List<MemberResponse> members;

	public static AssigneeCandidatesResponse from(
		AssigneeCandidatesInformation assigneeCandidatesInformation) {

		return new AssigneeCandidatesResponse(
			MemberResponse.from(assigneeCandidatesInformation.getAssignees()),
			MemberResponse.from(assigneeCandidatesInformation.getMembers())
		);
	}
}
