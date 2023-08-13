package com.issuetracker.issue.ui.dto.assignedlabel;

import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCreateData;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssignedLabelCreateRequest {

	private Long labelId;

	public AssignedLabelCreateData toAssignedLabelCreateData(Long issueId) {
		return new AssignedLabelCreateData(
			issueId,
			labelId
		);
	}
}
