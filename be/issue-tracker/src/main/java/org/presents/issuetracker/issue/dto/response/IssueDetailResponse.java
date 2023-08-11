package org.presents.issuetracker.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.comment.dto.response.CommentResponse;
import org.presents.issuetracker.issue.entity.vo.IssueDetailInfo;
import org.presents.issuetracker.label.dto.response.LabelPreviewResponse;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.user.dto.response.UserResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueDetailResponse {
	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;
	private int commentCount;
	private UserResponse author;
	private MilestonePreviewResponse milestone;
	private List<UserResponse> assignees;
	private List<LabelPreviewResponse> labels;
	private List<CommentResponse> comments;

	public static IssueDetailResponse from(IssueDetailInfo issueDetailInfo) {
		return IssueDetailResponse.builder()
			.id(issueDetailInfo.getId())
			.title(issueDetailInfo.getTitle())
			.contents(issueDetailInfo.getContents())
			.createdAt(issueDetailInfo.getCreatedAt())
			.status(issueDetailInfo.getStatus())
			.commentCount(issueDetailInfo.getComments().size())
			.author(UserResponse.from(issueDetailInfo.getAuthor()))
			.milestone(MilestonePreviewResponse.from(issueDetailInfo.getMilestone()))
			.assignees(issueDetailInfo.getAssignees().stream()
				.map(UserResponse::from)
				.collect(Collectors.toList()))
			.labels(issueDetailInfo.getLabels().stream()
				.map(LabelPreviewResponse::from)
				.collect(Collectors.toList()))
			.comments(issueDetailInfo.getComments().stream()
				.map(CommentResponse::from)
				.collect(Collectors.toList()))
			.build();
	}
}
