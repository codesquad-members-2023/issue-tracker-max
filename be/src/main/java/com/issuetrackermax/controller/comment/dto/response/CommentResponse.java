package com.issuetrackermax.controller.comment.dto.response;

import java.time.LocalDateTime;

import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
	private final Long id;
	private final String content;
	private final Long writerId;
	private final String writerLoginId;
	private final String writerImageUrl;
	private final LocalDateTime createdAt;

	@Builder
	public CommentResponse(Long id, String content, Long writerId, String writerLoginId, String writerImageUrl,
		LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.writerId = writerId;
		this.writerLoginId = writerLoginId;
		this.writerImageUrl = writerImageUrl;
		this.createdAt = createdAt;
	}

	public static CommentResponse from(Comment comment, Member member) {
		return CommentResponse.builder()
			.id(comment.getId())
			.content(comment.getContent())
			.writerId(comment.getWriterId())
			.writerLoginId(member.getLoginId())
			.writerImageUrl(member.getImageUrl())
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
