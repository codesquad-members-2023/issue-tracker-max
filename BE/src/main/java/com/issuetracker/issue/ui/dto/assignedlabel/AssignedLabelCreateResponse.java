package com.issuetracker.issue.ui.dto.assignedlabel;

import com.issuetracker.issue.application.dto.assignee.AssigneeCreateInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssignedLabelCreateResponse {

	private Long id;

	public static AssignedLabelCreateResponse from(AssigneeCreateInformation assigneeCreateInformation) {
		return new AssignedLabelCreateResponse(assigneeCreateInformation.getId());
	}
}
