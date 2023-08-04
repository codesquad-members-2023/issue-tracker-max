package org.presents.issuetracker.comment.entity;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Comment {
	private Long id;
	private Long issueId;
	private Long authorId;
	private String contents;
	private LocalDateTime createdAt;
	private boolean isDeleted;
}
