package com.issuetracker.member.ui.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.member.application.dto.MemberInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberResponse {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static MemberResponse from(MemberInformation memberInformation) {
		return new MemberResponse(
			memberInformation.getId(),
			memberInformation.getNickname(),
			memberInformation.getProfileImageUrl()
		);
	}

	public static List<MemberResponse> from(List<MemberInformation> memberInformation) {
		return memberInformation.stream()
			.map(MemberResponse::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
