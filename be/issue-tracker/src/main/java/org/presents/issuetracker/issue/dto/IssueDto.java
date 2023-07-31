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
	private Long issue_id;
	private Long author_id;
	private String title;
	private String contents;
	private LocalDateTime created_at;
	private String status;

}
