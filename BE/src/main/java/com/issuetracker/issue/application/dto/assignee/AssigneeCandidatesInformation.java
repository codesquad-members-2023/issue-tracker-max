package com.issuetracker.issue.application.dto.assignee;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.member.application.dto.MemberInformation;
import com.issuetracker.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssigneeCandidatesInformation {

	private List<MemberInformation> assignees;
	private List<MemberInformation> members;

	public static AssigneeCandidatesInformation from(List<Member> assignees, List<Member> members) {
		return new AssigneeCandidatesInformation(
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
			.map(AssigneeCandidatesInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
