package com.issuetracker.issue.ui.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.IssueAssigneeInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class IssueAssigneeResponse {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static IssueAssigneeResponse from(IssueAssigneeInformation issueAssigneeInformation) {
		return new IssueAssigneeResponse(
			issueAssigneeInformation.getId(),
			issueAssigneeInformation.getNickname(),
			issueAssigneeInformation.getProfileImageUrl()
		);
	}

	public static List<IssueAssigneeResponse> from(List<IssueAssigneeInformation> issueAssigneeInformation) {
		return issueAssigneeInformation.stream()
			.map(IssueAssigneeResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
