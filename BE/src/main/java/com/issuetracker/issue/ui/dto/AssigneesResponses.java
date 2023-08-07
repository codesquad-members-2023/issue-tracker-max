package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.member.ui.dto.MemberResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneesResponses {

	private List<MemberResponse> assignees;

	public static AssigneesResponses from(List<MemberInformation> memberInformation) {
		return new AssigneesResponses(MemberResponse.from(memberInformation));
	}
}
