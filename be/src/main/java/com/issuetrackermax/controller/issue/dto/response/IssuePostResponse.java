package com.issuetrackermax.controller.issue.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IssuePostResponse {
	private final Long id;

	public static IssuePostResponse from(Long id) {
		return new IssuePostResponse(id);
	}
}
