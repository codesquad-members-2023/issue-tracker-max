package org.presents.issuetracker.comment.entity;

import java.time.LocalDateTime;

import org.presents.issuetracker.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Comment {
	private Long commentId;
	private String contents;
	private LocalDateTime createdAt;
	private User author;
}
