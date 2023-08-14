package com.issuetrackermax.controller.comment.dto.response;

import java.time.LocalDateTime;

import com.issuetrackermax.domain.comment.entity.Comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
	private final Long id;
	private final String content;
	private final Long writerId;
	private final LocalDateTime createdAt;

	@Builder
	public CommentResponse(Long id, String content, Long writerId, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.writerId = writerId;
		this.createdAt = createdAt;
	}

	public static CommentResponse from(Comment comment) {
		return CommentResponse.builder()
			.id(comment.getId())
			.content(comment.getContent())
			.writerId(comment.getWriterId())
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
