package com.issuetrackermax.domain.comment.entity;

import java.time.LocalDateTime;

import com.issuetrackermax.controller.filter.dto.response.WriterResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentMemberVO {
	private final Long id;
	private final String content;
	private final Long issueId;
	private final WriterResponse writer;
	private final LocalDateTime createdAt;

	@Builder
	public CommentMemberVO(Long id, String content, Long issueId, WriterResponse writer, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.issueId = issueId;
		this.writer = writer;
		this.createdAt = createdAt;
	}
}
