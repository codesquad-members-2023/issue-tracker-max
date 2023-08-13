package com.issuetracker.issue.ui.dto.assignee;

import com.issuetracker.issue.application.dto.assignee.AssigneeCreateData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneeCreateRequest {

	private Long memberId;

	public AssigneeCreateData toAssigneeCreateData(Long issueId) {
		return new AssigneeCreateData(
			issueId,
			memberId
		);
	}
}
