package com.issuetracker.issue.domain.assignee;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AssigneeMember {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	@Builder
	public AssigneeMember(Long id, String nickname, String profileImageUrl) {
		this.id = id;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}
}
