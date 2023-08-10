package com.issuetracker.label.ui.dto;

import java.util.List;

import com.issuetracker.label.application.dto.LabelsInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelsResponse {

	private LabelCountMetadataResponse metadata;
	private List<LabelResponse> labels;

	public static LabelsResponse from(LabelsInformation labelsInformation) {
		return new LabelsResponse(
			LabelCountMetadataResponse.from(labelsInformation.getMetadata()),
			LabelResponse.from(labelsInformation.getLabels())
		);
	}
}
