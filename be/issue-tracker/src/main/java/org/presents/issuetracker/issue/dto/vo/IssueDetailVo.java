package org.presents.issuetracker.issue.dto.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.presents.issuetracker.comment.entity.Comment;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.user.entity.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class IssueDetailVo {
	private Long issueId;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;
	private User author;
	private Milestone milestone;
	private List<User> assignees;
	private List<Label> labels;
	private List<Comment> comments;
}
