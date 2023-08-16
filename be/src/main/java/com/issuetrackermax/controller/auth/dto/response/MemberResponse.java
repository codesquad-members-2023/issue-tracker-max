package com.issuetrackermax.controller.auth.dto.response;

import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
	private Long id;
	private String name;
	private String imageUrl;

	@Builder
	public MemberResponse(Long id, String name, String imageUrl) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public static MemberResponse from(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getNickName())
			.imageUrl(member.getImageUrl())
			.build();
	}
}
