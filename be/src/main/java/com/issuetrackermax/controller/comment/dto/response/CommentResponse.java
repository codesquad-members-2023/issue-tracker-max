package com.issuetrackermax.controller.comment.dto.response;

import java.time.LocalDateTime;

import com.issuetrackermax.controller.filter.dto.response.WriterResponse;
import com.issuetrackermax.domain.comment.entity.Comment;
import com.issuetrackermax.domain.member.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
	private final Long id;
	private final String content;
	private final WriterResponse writer;
	private final LocalDateTime createdAt;

	@Builder
	public CommentResponse(Long id, String content, WriterResponse writer, LocalDateTime createdAt) {
		this.id = id;
		this.content = content;
		this.writer = writer;
		this.createdAt = createdAt;
	}

	public static CommentResponse from(Comment comment, Member member) {
		return CommentResponse.builder()
			.id(comment.getId())
			.content(comment.getContent())
			.writer(WriterResponse.builder()
				.id(member.getId())
				.name(member.getNickName())
				.imageUrl(member.getImageUrl()).build())
			.createdAt(comment.getCreatedAt())
			.build();
	}
}
