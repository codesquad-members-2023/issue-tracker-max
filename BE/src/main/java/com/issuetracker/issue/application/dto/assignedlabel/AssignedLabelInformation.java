package com.issuetracker.issue.application.dto.assignedlabel;

import com.issuetracker.issue.application.dto.assignee.AssigneeCreateInformation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssignedLabelInformation {

	private Long id;

	public static AssignedLabelInformation from(long id) {
		return new AssignedLabelInformation(id);
	}
}
