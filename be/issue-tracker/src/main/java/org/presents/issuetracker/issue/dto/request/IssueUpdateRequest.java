package org.presents.issuetracker.issue.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueUpdateRequest {
	private Long id;
	private String title;
	private String contents;
}
