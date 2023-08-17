package com.issuetracker.issue.application.dto.assignee;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.domain.assignee.AssigneeMember;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssigneeMemberInformation {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static AssigneeMemberInformation from(AssigneeMember assigneeMember) {
		return new AssigneeMemberInformation(
			assigneeMember.getId(),
			assigneeMember.getNickname(),
			assigneeMember.getProfileImageUrl()
		);
	}

	public static List<AssigneeMemberInformation> from(List<AssigneeMember> assigneeMembers) {
		return assigneeMembers.stream()
			.map(AssigneeMemberInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
