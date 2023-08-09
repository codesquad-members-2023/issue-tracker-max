package com.issuetracker.issue.application.dto.assignee;

import com.issuetracker.issue.domain.assignee.Assignee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssigneeCreateData {

	private Long issueId;
	private Long memberId;

	public Assignee toAssignee() {
		return Assignee.builder()
			.issueId(issueId)
			.memberId(memberId)
			.build();
	}
}
