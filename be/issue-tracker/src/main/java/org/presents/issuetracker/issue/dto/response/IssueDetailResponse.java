package org.presents.issuetracker.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.comment.dto.response.CommentResponse;
import org.presents.issuetracker.issue.entity.vo.IssueDetailVo;
import org.presents.issuetracker.label.dto.response.LabelResponse;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.user.dto.response.UserResponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueDetailResponse {
	private Long issueId;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;
	private UserResponse author;
	private MilestonePreviewResponse milestone;
	private List<UserResponse> assignees;
	private List<LabelResponse> labels;
	private List<CommentResponse> comments;

	public static IssueDetailResponse fromVo(IssueDetailVo issueDetailVo) {
		return IssueDetailResponse.builder()
			.issueId(issueDetailVo.getId())
			.title(issueDetailVo.getTitle())
			.contents(issueDetailVo.getContents())
			.createdAt(issueDetailVo.getCreatedAt())
			.status(issueDetailVo.getStatus())
			.author(UserResponse.fromEntity(issueDetailVo.getAuthor()))
			.milestone(MilestonePreviewResponse.fromVo(issueDetailVo.getMilestone()))
			.assignees(issueDetailVo.getAssignees().stream()
				.map(UserResponse::fromEntity)
				.collect(Collectors.toList()))
			.labels(issueDetailVo.getLabels().stream()
				.map(LabelResponse::fromEntity)
				.collect(Collectors.toList()))
			.comments(issueDetailVo.getComments().stream()
				.map(CommentResponse::fromVo)
				.collect(Collectors.toList()))
			.build();
	}
}
