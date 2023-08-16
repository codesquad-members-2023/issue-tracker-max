package com.issuetrackermax.controller.comment.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentModifyRequest {
	private final String content;

	@Builder
	public CommentModifyRequest(String content) {
		this.content = content;
	}
}
