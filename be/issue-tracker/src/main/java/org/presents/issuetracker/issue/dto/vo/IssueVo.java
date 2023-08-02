package org.presents.issuetracker.issue.dto.vo;

import java.time.LocalDateTime;
import java.util.List;

import org.presents.issuetracker.issue.entity.Author;
import org.presents.issuetracker.label.entity.Label;
import org.presents.issuetracker.milestone.entity.Milestone;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IssueVo {
	// issue_id, author_id, milestone_id, title, contents, created_at, status
	@JsonProperty("id")
	private Long issueId;
	private String title;
	private Author author;
	private List<Label> labels;
	private Milestone milestone;
	private LocalDateTime createdAt;
	private String status;

}
