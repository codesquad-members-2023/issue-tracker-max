package com.issuetrackermax.controller.comment.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentModifyRequest {
	@NotBlank(message = "내용을 입력해주세요.")
	private String content;

	@Builder
	public CommentModifyRequest(String content) {
		this.content = content;
	}
}
