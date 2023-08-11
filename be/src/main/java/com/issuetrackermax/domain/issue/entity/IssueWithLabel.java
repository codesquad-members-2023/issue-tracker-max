package com.issuetrackermax.domain.issue.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class IssueWithLabel {
	private Long id;
	private Long issueId;
	private Long labelId;

	@Builder
	public IssueWithLabel(Long id, Long issueId, Long labelId) {
		this.id = id;
		this.issueId = issueId;
		this.labelId = labelId;
	}
}
