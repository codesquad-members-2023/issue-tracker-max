package com.issuetrackermax.controller.comment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {
	private String content;

	@Builder
	public CommentCreateRequest(Long issueId, String content) {
		this.content = content;
	}
}
