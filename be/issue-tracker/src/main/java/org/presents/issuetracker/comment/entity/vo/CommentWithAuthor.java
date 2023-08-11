package org.presents.issuetracker.comment.entity.vo;

import java.time.LocalDateTime;

import org.presents.issuetracker.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentWithAuthor {
	private Long id;
	private String contents;
	private LocalDateTime createdAt;
	private User author;
}
