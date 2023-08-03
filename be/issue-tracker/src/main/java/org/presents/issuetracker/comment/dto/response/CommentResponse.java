package org.presents.issuetracker.comment.dto.response;

import java.time.LocalDateTime;

import org.presents.issuetracker.comment.entity.vo.CommentVo;
import org.presents.issuetracker.user.dto.response.UserResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {
	private Long id;
	private String contents;
	private LocalDateTime createdAt;
	private UserResponse author;

	@Builder
	private CommentResponse(Long id, String contents, LocalDateTime createdAt, UserResponse author) {
		this.id = id;
		this.contents = contents;
		this.createdAt = createdAt;
		this.author = author;
	}

	public static CommentResponse fromVo(CommentVo commentVo) {
		return CommentResponse.builder()
			.id(commentVo.getId())
			.contents(commentVo.getContents())
			.createdAt(commentVo.getCreatedAt())
			.author(UserResponse.fromEntity(commentVo.getAuthor()))
			.build();
	}
}
