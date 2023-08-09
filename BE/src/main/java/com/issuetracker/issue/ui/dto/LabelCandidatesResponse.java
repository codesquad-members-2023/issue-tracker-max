package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.issue.application.dto.LabelCandidatesInformation;
import com.issuetracker.label.ui.dto.LabelResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelCandidatesResponse {

	private List<LabelResponse> assignedLabels;
	private List<LabelResponse> labels;

	public static LabelCandidatesResponse from(
		LabelCandidatesInformation labelCandidatesInformation) {

		return new LabelCandidatesResponse(
			LabelResponse.from(labelCandidatesInformation.getAssignedLabels()),
			LabelResponse.from(labelCandidatesInformation.getLabels())
		);
	}
}
