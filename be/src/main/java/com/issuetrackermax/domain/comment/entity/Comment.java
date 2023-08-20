package com.issuetrackermax.domain.comment.entity;

import java.time.LocalDateTime;

import com.issuetrackermax.controller.comment.dto.request.CommentCreateRequest;
import com.issuetrackermax.controller.comment.dto.request.CommentModifyRequest;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {
	private final Long id;
	private final String content;
	private final Long issueId;
	private final Long writerId;
	private final LocalDateTime createdAt;

	@Builder
	public Comment(Long id, String content, Long issueId, Long writerId, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.issueId = issueId;
		this.writerId = writerId;
		this.createdAt = createdAt;
	}

	public static Comment from(CommentModifyRequest commentModifyRequest) {
		return Comment.builder()
			.content(commentModifyRequest.getContent())
			.build();
	}

	public static Comment from(CommentCreateRequest commentCreateRequest, Long issueId, Long memberId) {
		return Comment.builder()
			.issueId(issueId)
			.content(commentCreateRequest.getContent())
			.writerId(memberId)
			.build();
	}
}
