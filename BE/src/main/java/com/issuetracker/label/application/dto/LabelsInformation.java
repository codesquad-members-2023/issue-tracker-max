package com.issuetracker.label.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LabelsInformation {

	private LabelCountMetadataInformation metadata;
	private List<LabelInformation> labels;

	public static LabelsInformation from(LabelCountMetadataInformation metadata, List<LabelInformation> labels) {
		return new LabelsInformation(
			metadata,
			labels
		);
	}
}
