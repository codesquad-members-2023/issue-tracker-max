package com.issuetracker.issue.ui.dto.assignee;

import com.issuetracker.issue.application.dto.assignee.AssigneeCreateInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneesCreateResponse {

	private Long id;

	public static AssigneesCreateResponse from(AssigneeCreateInformation assigneeCreateInformation) {
		return new AssigneesCreateResponse(assigneeCreateInformation.getId());
	}
}
