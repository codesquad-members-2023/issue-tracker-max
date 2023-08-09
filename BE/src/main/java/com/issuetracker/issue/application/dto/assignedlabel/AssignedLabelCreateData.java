package com.issuetracker.issue.application.dto.assignedlabel;

import com.issuetracker.issue.domain.assignedlabel.AssignedLabel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AssignedLabelCreateData {

	private Long issueId;
	private Long labelId;

	public AssignedLabel toAssignedLabel() {
		return AssignedLabel.builder()
			.issueId(issueId)
			.labelId(labelId)
			.build();
	}
}
