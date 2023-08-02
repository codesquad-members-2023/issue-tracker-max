package com.issuetrackermax.controller.issue.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IssueTitleRequest {
	private String title;

	@Builder
	public IssueTitleRequest(String title) {
		this.title = title;
	}
}
