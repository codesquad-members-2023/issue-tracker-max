package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.LabelsForIssueUpdateInformation;
import com.issuetracker.label.ui.dto.LabelResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelsForIssueUpdateResponse {

	private List<LabelResponse> assignedLabels;
	private List<LabelResponse> labels;

	public static LabelsForIssueUpdateResponse from(
		LabelsForIssueUpdateInformation labelsForIssueUpdateInformation) {

		return new LabelsForIssueUpdateResponse(
			LabelResponse.from(labelsForIssueUpdateInformation.getAssignedLabels()),
			LabelResponse.from(labelsForIssueUpdateInformation.getLabels())
		);
	}
}
