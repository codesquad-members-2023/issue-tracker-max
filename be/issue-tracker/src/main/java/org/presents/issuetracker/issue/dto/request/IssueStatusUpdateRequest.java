package org.presents.issuetracker.issue.dto.request;

import java.util.List;

import lombok.Getter;

@Getter
public class IssueStatusUpdateRequest {
	private List<Long> issueIds;
	private String status;
}
