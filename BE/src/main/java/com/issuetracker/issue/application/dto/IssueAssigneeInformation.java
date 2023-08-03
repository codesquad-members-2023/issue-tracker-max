package com.issuetracker.issue.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueAssigneeInformation {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static IssueAssigneeInformation from(Member assignee) {
		return new IssueAssigneeInformation(
			assignee.getId(),
			assignee.getNickname(),
			assignee.getProFileImageUrl()
		);
	}

	public static List<IssueAssigneeInformation> from(List<Member> assignees) {
		return assignees.stream()
			.map(IssueAssigneeInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
