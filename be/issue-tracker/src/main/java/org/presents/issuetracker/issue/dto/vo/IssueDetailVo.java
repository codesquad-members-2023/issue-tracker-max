package org.presents.issuetracker.issue.dto.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.presents.issuetracker.comment.entity.Comment;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueDetailVo {
	private final Long issueId;
	private final String title;
	private final String contents;
	private final LocalDateTime createdAt;
	private final String status;
	private final User author;
	private final Milestone milestone;
	private final List<User> assignees;
	private final List<Label> labels;
	private final List<Comment> comments;
}
