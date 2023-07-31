package com.issuetrackermax.domain.comment.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {
	private Long id;
	private String content;
	private String imageUrl;
	private Long writerId;
	private LocalDateTime createdAt;

	@Builder
	public Comment(Long id, String content, String imageUrl, Long writerId, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.imageUrl = imageUrl;
		this.writerId = writerId;
		this.createdAt = createdAt;
	}
}
