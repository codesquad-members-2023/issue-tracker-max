package com.issuetrackermax.controller.member.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberContentResponse {
	private final Long id;
	private final String name;

	@Builder
	public MemberContentResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static List<MemberContentResponse> from(List<Member> members) {
		return members.stream().map(MemberContentResponse::from).collect(
			Collectors.toList());
	}

	public static MemberContentResponse from(Member member) {
		return MemberContentResponse.builder()
			.id(member.getId())
			.name(member.getNickName())
			.build();
	}
}
