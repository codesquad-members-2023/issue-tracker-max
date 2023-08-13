package com.issuetracker.label.ui.dto;

import com.issuetracker.label.application.dto.LabelCountMetadataInformation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class LabelCountMetadataResponse {

	private int totalLabelCount;
	private int totalMilestoneCount;

	public static LabelCountMetadataResponse from(LabelCountMetadataInformation metadataInformation) {
		return new LabelCountMetadataResponse(
			metadataInformation.getTotalLabelCount(),
			metadataInformation.getTotalMilestoneCount()
		);
	}
}
