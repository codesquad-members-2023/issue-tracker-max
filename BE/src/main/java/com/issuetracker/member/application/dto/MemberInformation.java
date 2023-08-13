package com.issuetracker.member.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetracker.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInformation {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public static MemberInformation from(Member member) {
		return new MemberInformation(
			member.getId(),
			member.getNickname(),
			member.getProfileImageUrl()
		);
	}

	public static List<MemberInformation> from(List<Member> members) {
		return members.stream()
			.map(MemberInformation::from)
			.collect(Collectors.toUnmodifiableList());
	}
}
