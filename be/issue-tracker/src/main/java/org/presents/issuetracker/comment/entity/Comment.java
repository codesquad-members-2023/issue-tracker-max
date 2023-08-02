package org.presents.issuetracker.comment.entity;

import java.time.LocalDateTime;

import org.presents.issuetracker.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
	private Long commentId;
	private String contents;
	private LocalDateTime createdAt;
	private User author;
}
