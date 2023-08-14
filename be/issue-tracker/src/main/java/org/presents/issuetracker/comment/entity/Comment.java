package org.presents.issuetracker.comment.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {
	private Long id;
	private Long issueId;
	private Long authorId;
	private String contents;
	private LocalDateTime createdAt;
	private boolean isDeleted;

	@Builder
	private Comment(Long issueId, Long authorId, String contents) {
		this.issueId = issueId;
		this.authorId = authorId;
		this.contents = contents;
	}

	private Comment(Long id, String contents) {
		this.id = id;
		this.contents = contents;
	}

	private Comment(Long id, Long issueId, Long authorId, String contents, LocalDateTime createdAt) {
		this.id = id;
		this.issueId = issueId;
		this.authorId = authorId;
		this.contents = contents;
		this.createdAt = createdAt;
	}

	public static Comment of(Long id, String contents) {
		return new Comment(id, contents);
	}

	public static Comment of(Long id, Long issueId, Long authorId, String contents, LocalDateTime createdAt) {
		return new Comment(id, issueId, authorId, contents, createdAt);
	}
}
