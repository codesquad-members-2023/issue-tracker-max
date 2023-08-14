package com.issuetrackermax.domain.comment.entity;

import java.time.LocalDateTime;

import com.issuetrackermax.controller.comment.dto.request.CommentCreateRequest;
import com.issuetrackermax.controller.comment.dto.request.CommentRequest;

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

	public static Comment from(CommentRequest commentRequest) {
		return Comment.builder()
			.content(commentRequest.getContent())
			.build();
	}

	public static Comment from(CommentCreateRequest commentCreateRequest, Long memberId) {
		return Comment.builder()
			.issueId(commentCreateRequest.getIssueId())
			.content(commentCreateRequest.getContent())
			.writerId(memberId)
			.build();
	}
}
