package com.issuetracker.label.application.dto;

import com.issuetracker.label.domain.LabelCountMetadata;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LabelCountMetadataInformation {

	private int totalLabelCount;
	private int totalMilestoneCount;

	public static LabelCountMetadataInformation from(LabelCountMetadata metadata) {
		return new LabelCountMetadataInformation(
			metadata.getTotalLabelCount(),
			metadata.getTotalMilestoneCount()
		);
	}
}
