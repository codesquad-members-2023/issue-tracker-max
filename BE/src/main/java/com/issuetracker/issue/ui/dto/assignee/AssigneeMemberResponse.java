package com.issuetracker.issue.ui.dto.assignee;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.issue.application.dto.assignee.AssigneeMemberInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneeMemberResponse {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static AssigneeMemberResponse from(AssigneeMemberInformation assigneeMemberInformation) {
		return new AssigneeMemberResponse(
			assigneeMemberInformation.getId(),
			assigneeMemberInformation.getNickname(),
			assigneeMemberInformation.getProfileImageUrl()
		);
	}

	public static List<AssigneeMemberResponse> from(List<AssigneeMemberInformation> assigneeMemberInformations) {
		return assigneeMemberInformations.stream()
			.map(AssigneeMemberResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
