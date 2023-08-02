package org.presents.issuetracker.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.comment.dto.response.CommentResponseDto;
import org.presents.issuetracker.issue.dto.vo.IssueDetailVo;
import org.presents.issuetracker.label.dto.response.LabelResponseDto;
import org.presents.issuetracker.milestone.dto.response.MilestonePreviewResponse;
import org.presents.issuetracker.user.dto.response.UserResponseDto;

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
	private UserResponseDto author;
	private MilestonePreviewResponse milestone;
	private List<UserResponseDto> assignees;
	private List<LabelResponseDto> labels;
	private List<CommentResponseDto> comments;

	public static IssueDetailResponse fromVo(IssueDetailVo issueDetailVo) {
		return IssueDetailResponse.builder()
			.issueId(issueDetailVo.getIssueId())
			.title(issueDetailVo.getTitle())
			.contents(issueDetailVo.getContents())
			.createdAt(issueDetailVo.getCreatedAt())
			.status(issueDetailVo.getStatus())
			.author(UserResponseDto.fromEntity(issueDetailVo.getAuthor()))
			.milestone(MilestonePreviewResponse.fromEntity(issueDetailVo.getMilestone()))
			.assignees(issueDetailVo.getAssignees().stream()
				.map(UserResponseDto::fromEntity)
				.collect(Collectors.toList()))
			.labels(issueDetailVo.getLabels().stream()
				.map(LabelResponseDto::fromEntity)
				.collect(Collectors.toList()))
			.comments(issueDetailVo.getComments().stream()
				.map(CommentResponseDto::fromEntity)
				.collect(Collectors.toList()))
			.build();
	}
}
