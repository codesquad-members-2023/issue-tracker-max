package org.presents.issuetracker.issue.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IssueStatusUpdateRequest {
	private List<Long> issueIds;
	private String status;
}
