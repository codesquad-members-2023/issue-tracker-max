package com.issuetracker.issue.ui.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.AssigneeInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneeResponse {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static AssigneeResponse from(AssigneeInformation assigneeInformation) {
		return new AssigneeResponse(
			assigneeInformation.getId(),
			assigneeInformation.getNickname(),
			assigneeInformation.getProfileImageUrl()
		);
	}

	public static List<AssigneeResponse> from(List<AssigneeInformation> assigneeInformation) {
		return assigneeInformation.stream()
			.map(AssigneeResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
