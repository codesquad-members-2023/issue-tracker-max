package org.presents.issuetracker.comment.dto.response;

import java.time.LocalDateTime;

import org.presents.issuetracker.comment.entity.vo.CommentWithAuthor;
import org.presents.issuetracker.user.dto.response.UserResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
	private Long id;
	private String contents;
	private LocalDateTime createdAt;
	private UserResponse author;

	public static CommentResponse from(CommentWithAuthor comment) {
		return CommentResponse.builder()
			.id(comment.getId())
			.contents(comment.getContents())
			.createdAt(comment.getCreatedAt())
			.author(UserResponse.from(comment.getAuthor()))
			.build();
	}

	private CommentResponse(Long id, String contents, LocalDateTime createdAt, UserResponse author) {
		this.id = id;
		this.contents = contents;
		this.createdAt = createdAt;
		this.author = author;
	}

	public static CommentResponse of(Long id, String contents, LocalDateTime createdAt, UserResponse author) {
		return new CommentResponse(id, contents, createdAt, author);
	}
}
