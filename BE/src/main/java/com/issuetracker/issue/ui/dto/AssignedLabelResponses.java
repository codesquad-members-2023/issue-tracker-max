package com.issuetracker.issue.ui.dto;

import java.util.List;

import com.issuetracker.label.application.dto.LabelInformation;
import com.issuetracker.label.ui.dto.LabelResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AssignedLabelResponses {

	private List<LabelResponse> labels;

	public static AssignedLabelResponses from(List<LabelInformation> labelInformation) {
		return new AssignedLabelResponses(LabelResponse.from(labelInformation));
	}
}
