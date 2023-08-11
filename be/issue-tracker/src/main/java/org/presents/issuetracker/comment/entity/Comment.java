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
}
