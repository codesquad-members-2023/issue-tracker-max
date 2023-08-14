package com.issuetrackermax.controller.comment.dto.response;

import java.time.LocalDateTime;

import com.issuetrackermax.domain.comment.entity.Comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
	private final Long id;
	private final String content;
	private final LocalDateTime createdAt;

	@Builder
	public CommentResponse(Long id, String content, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.createdAt = createdAt;
	}

	public static CommentResponse from(Comment comment) {
		return CommentResponse.builder()
			.id(comment.getId())
			.content(comment.getContent())
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
