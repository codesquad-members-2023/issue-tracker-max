package com.issuetracker.issue.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Assignee {

	private Long issueId;
	private Long memberId;

	@Builder
	private Assignee(Long issueId, Long memberId) {
		this.issueId = issueId;
		this.memberId = memberId;
	}
}
