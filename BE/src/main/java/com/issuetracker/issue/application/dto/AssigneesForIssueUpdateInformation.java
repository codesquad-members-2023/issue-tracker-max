package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssigneesForIssueUpdateInformation {

	private List<MemberInformation> assignees;
	private List<MemberInformation> members;

	public static AssigneesForIssueUpdateInformation from(List<Member> assignees, List<Member> members) {
		return new AssigneesForIssueUpdateInformation(
			from(assignees),
			from(members)
		);
	}

	private static MemberInformation from(Member assignee) {
		return new MemberInformation(
			assignee.getId(),
			assignee.getNickname(),
			assignee.getProfileImageUrl()
		);
	}

	private static List<MemberInformation> from(List<Member> assignees) {
		return assignees.stream()
			.map(AssigneesForIssueUpdateInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
