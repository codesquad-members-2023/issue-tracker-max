package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssigneeInformation {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static AssigneeInformation from(Member assignee) {
		return new AssigneeInformation(
			assignee.getId(),
			assignee.getNickname(),
			assignee.getProFileImageUrl()
		);
	}

	public static List<AssigneeInformation> from(List<Member> assignees) {
		return assignees.stream()
			.map(AssigneeInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
