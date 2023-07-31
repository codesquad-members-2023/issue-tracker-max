package org.presents.issuetracker.issue.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class IssueDto {
	// issue_id, author_id, title, contents, created_at, status
	private Long issueId;
	private Long authorId;
	private String title;
	private String contents;
	private LocalDateTime createdAt;
	private String status;

}
