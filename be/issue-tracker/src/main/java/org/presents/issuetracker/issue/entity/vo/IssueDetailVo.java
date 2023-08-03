package org.presents.issuetracker.issue.entity.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.presents.issuetracker.comment.entity.vo.CommentVo;
import org.presents.issuetracker.label.entity.vo.LabelVo;
import org.presents.issuetracker.milestone.entity.vo.MilestonePreviewVo;
import org.presents.issuetracker.user.entity.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IssueDetailVo {
	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;
	private User author;
	private MilestonePreviewVo milestone;
	private List<User> assignees;
	private List<LabelVo> labels;
	private List<CommentVo> comments;
}
