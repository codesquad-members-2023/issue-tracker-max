package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.AssigneeInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssigneesResponse {

	private List<AssigneeResponse> assignees;

	public static AssigneesResponse from(List<AssigneeInformation> assigneeInformation) {
		return new AssigneesResponse(AssigneeResponse.from(assigneeInformation));
	}
}
