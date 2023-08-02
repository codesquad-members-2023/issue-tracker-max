package com.issuetrackermax.controller.issue.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssuesStatusRequest {
	private List<Long> issueIds;
	private String issueStatus;

	@Builder
	public IssuesStatusRequest(List<Long> issueIds, String issueStatus) {
		this.issueIds = issueIds;
		this.issueStatus = issueStatus;
	}
}
