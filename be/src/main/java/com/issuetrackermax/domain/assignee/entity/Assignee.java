package com.issuetrackermax.domain.assignee.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Assignee {
	private Long id;
	private Long issueId;
	private Long memberid;

	@Builder
	public Assignee(Long id, Long issueId, Long memberid) {
		this.id = id;
		this.issueId = issueId;
		this.memberid = memberid;
	}
}
