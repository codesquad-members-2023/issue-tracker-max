package com.issuetrackermax.controller.issue.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueApplyRequest {
	private List<Long> ids;

	@Builder
	public IssueApplyRequest(List<Long> ids) {
		this.ids = ids;
	}
}
