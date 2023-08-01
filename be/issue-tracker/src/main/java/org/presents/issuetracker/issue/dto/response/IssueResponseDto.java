package org.presents.issuetracker.issue.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.presents.issuetracker.issue.entity.Issue;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;
import org.presents.issuetracker.user.entity.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IssueResponseDto {
	// issue_id, author_id, milestone_id, title, contents, created_at, status
	@JsonProperty("id")
	private Long issueId;
	private String title;
	private UserResponseDto author;
	private List<LabelResponseDto> labels;
	private MilestoneResponseDto milestone;
	private LocalDateTime createdAt;
	private String status;

	public static IssueResponseDto fromEntity(Issue issue, User user, List<Label> labels,
		Milestone milestone) {
		return IssueResponseDto.builder()
			.issueId(issue.getIssueId())
			.title(issue.getTitle())
			.author(UserResponseDto.fromEntity(user))
			.labels(labels.stream().map(LabelResponseDto::fromEntity).collect(Collectors.toList()))
			.milestone(MilestoneResponseDto.fromEntity(milestone)).build();
	}

}
