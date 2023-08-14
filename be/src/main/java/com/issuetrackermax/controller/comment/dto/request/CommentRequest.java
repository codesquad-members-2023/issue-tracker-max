package com.issuetrackermax.controller.comment.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentRequest {
	private final String content;

	@Builder
	public CommentRequest(String content) {
		this.content = content;
	}
}
