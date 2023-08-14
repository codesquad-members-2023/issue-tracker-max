package com.issuetrackermax.controller.comment.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
	private final String content;

	@Builder
	public CommentCreateRequest(String content) {
		this.content = content;
	}
}
