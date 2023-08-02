package org.presents.issuetracker.comment.dto.response;

import java.time.LocalDateTime;

import org.presents.issuetracker.comment.entity.Comment;
import org.presents.issuetracker.user.dto.response.UserResponseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponseDto {
	private Long id;
	private String contents;
	private LocalDateTime createdAt;
	private UserResponseDto author;

	public static CommentResponseDto fromEntity(Comment comment) {
		return CommentResponseDto.builder()
			.id(comment.getCommentId())
			.contents(comment.getContents())
			.createdAt(comment.getCreatedAt())
			.author(UserResponseDto.fromEntity(comment.getAuthor()))
			.build();
	}
}
