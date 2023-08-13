package com.issuetracker.issue.application.dto.assignee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssigneeCreateInformation {

	private Long id;

	public static AssigneeCreateInformation from(long id) {
		return new AssigneeCreateInformation(id);
	}
}
