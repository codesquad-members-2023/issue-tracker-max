package org.presents.issuetracker.comment.entity.vo;

import java.time.LocalDateTime;

import org.presents.issuetracker.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentVo {
	private Long id;
	private String contents;
	private LocalDateTime createdAt;
	private User author;
}
