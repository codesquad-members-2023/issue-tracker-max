package com.issuetrackermax.controller.comment.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
	private final Long issueId;
	private final String content;

	@Builder
	public CommentCreateRequest(Long issueId, String content) {
		this.issueId = issueId;
		this.content = content;
	}
}
