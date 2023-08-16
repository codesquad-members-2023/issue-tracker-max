package com.issuetrackermax.domain.comment.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentMemberVO {
	private final Long id;
	private final String content;
	private final Long issueId;
	private final Long writerId;
	private final String writerLoginId;
	private final String writerImageUrl;
	private final LocalDateTime createdAt;

	@Builder
	public CommentMemberVO(Long id, String content, Long issueId, Long writerId, String writerLoginId,
		String writerImageUrl, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.issueId = issueId;
		this.writerId = writerId;
		this.writerLoginId = writerLoginId;
		this.writerImageUrl = writerImageUrl;
		this.createdAt = createdAt;
	}
}
