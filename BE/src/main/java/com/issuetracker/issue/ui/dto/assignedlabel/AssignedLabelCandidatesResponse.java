package com.issuetracker.issue.ui.dto.assignedlabel;

import java.util.List;

import com.issuetracker.issue.application.dto.assignedlabel.AssignedLabelCandidatesInformation;
import com.issuetracker.label.ui.dto.LabelResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssignedLabelCandidatesResponse {

	private List<LabelResponse> assignedLabels;
	private List<LabelResponse> labels;

	public static AssignedLabelCandidatesResponse from(
		AssignedLabelCandidatesInformation assignedLabelCandidatesInformation) {

		return new AssignedLabelCandidatesResponse(
			LabelResponse.from(assignedLabelCandidatesInformation.getAssignedLabels()),
			LabelResponse.from(assignedLabelCandidatesInformation.getLabels())
		);
	}
}
