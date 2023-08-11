package org.presents.issuetracker.issue.entity.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.presents.issuetracker.comment.entity.vo.CommentWithAuthor;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.vo.MilestonePreview;
import org.presents.issuetracker.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueDetailInfo {
	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;
	private User author;
	private MilestonePreview milestone;
	private List<User> assignees;
	private List<Label> labels;
	private List<CommentWithAuthor> comments;
}
